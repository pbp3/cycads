/*
 * Created on 04/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.SQL.EntitySQL;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.note.Type;
import org.cycads.entities.note.SQL.TypeSQL;

public class AnnotationSQL<SO extends EntitySQL, TA extends EntitySQL> extends AssociationSQL<SO, TA>
		implements Annotation<SO, TA>
{
	public final static String		PARENT_TYPE_NAME	= "Parent";

	private String					score;

	private AnnotationMethodSQL		method;
	private Collection<EntitySQL>	parents;

	public AnnotationSQL(int id, Connection con) throws SQLException {
		super(id, con);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT annotation_method_id, score from Annotation WHERE annotation_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				method = AnnotationMethodSQL.getMethod(rs.getInt("annotation_method_id"), con);
				score = rs.getString("score");
			}
			else {
				throw new SQLException("Annotation does not exist:" + id);
			}
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
	}

	public static <SO extends EntitySQL, TA extends EntitySQL> AnnotationSQL<SO, TA> createAnnotationSQL(SO source,
			TA target, Collection<Type> types, AnnotationMethod method, String score, Connection con)
			throws SQLException {
		AnnotationMethodSQL methodSQL = AnnotationMethodSQL.getMethod(method, con);

		//annotation with the same source, target, method and score already exists? 
		Collection< ? extends AnnotationSQL<SO, TA>> annots = getAnnotations(source, target, methodSQL, types, con);
		for (AnnotationSQL<SO, TA> annot : annots) {
			String annotScore = annot.getScore();
			if (annotScore == null) {
				if (score == null) {
					return null;
				}
			}
			else if (annotScore.equals(score)) {
				return null;
			}
		}

		AssociationSQL<SO, TA> association = AssociationSQL.createAssociationSQL(source, target, types, con);
		association.addType(getEntityType(con));
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("INSERT INTO Annotation (annotation_id, annotation_method_id, score) VALUES (?,?,?)");
			stmt.setInt(1, association.getId());
			stmt.setInt(2, methodSQL.getId());
			stmt.setString(3, score);
			stmt.executeUpdate();
			return new AnnotationSQL<SO, TA>(association.getId(), con);
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
	}

	@Override
	public AnnotationMethodSQL getAnnotationMethod() {
		return method;
	}

	@Override
	public String getScore() {
		return score;
	}

	@Override
	public void setScore(String score) {
		this.score = score;
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement("UPDATE Annotation SET score=? WHERE annotation_id=?");
			stmt.setString(1, score);
			stmt.setInt(2, getId());
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new RuntimeException("Can't set score value of the Annotation:" + getId());
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
	}

	@Override
	public void addParent(EntityObject parent) {
		if (!(parent instanceof EntitySQL)) {
			throw new RuntimeException("Parent is not a SQL entity.");
		}
		EntitySQL parentSQL = (EntitySQL) parent;
		if (!isParent(parentSQL)) {
			try {
				AssociationSQL< ? , ? > parentAssociation = AssociationSQL.createAssociationSQL(this, parentSQL,
					Arrays.asList(getParentType()), getConnection());
			}
			catch (SQLException e) {
				throw new RuntimeException("Can not create the parent association.", e);
			}
			parents.add(parentSQL);
		}
	}

	@Override
	public Collection<EntitySQL> getParents() {
		if (parents == null) {
			Collection<AssociationSQL<AnnotationSQL<SO, TA>, ? >> associations;
			try {
				associations = AssociationSQL.getAssociations(this, (Type) null, Arrays.asList(getParentType()),
					getConnection());
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
			for (AssociationSQL< ? , ? > association : associations) {
				parents.add(association.getTarget());
			}
		}
		return parents;
	}

	public boolean isParent(EntitySQL parent) {
		for (EntitySQL parent1 : getParents()) {
			if (parent.getId() == parent1.getId() && parent.getEntityType().equals(parent1.getEntityType())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TypeSQL getEntityType() {
		return getEntityType(getConnection());
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(Annotation.ENTITY_TYPE_NAME, con);
	}

	public TypeSQL getParentType() {
		return getParentType(getConnection());
	}

	public static TypeSQL getParentType(Connection con) {
		return TypeSQL.getType(PARENT_TYPE_NAME, con);
	}

	public static <SO extends EntitySQL, TA extends EntitySQL> Collection<AnnotationSQL<SO, TA>> getAnnotations(
			SO source, TA target, AnnotationMethod method, Collection<Type> types, Connection con) throws SQLException {
		StringBuffer query = new StringBuffer(
			"SELECT annotation_id FROM Annotation AN, Association AS, Method M, Source_target_type S, Association_type T WHERE");
		query.append(" AN.annotation_id = AS.association_id AND AN.annotation_method_id = M.method_id");
		query.append(" AND AS.association_id=T.association_id AND AS.source_target_type_id=S.source_target_type_id");
		TypeSQL typeSQL;

		if (source != null) {
			query.append(" AND source_id=" + source.getId());
			typeSQL = TypeSQL.getType(source.getEntityType(), con);
			query.append(" AND source_type_id=" + typeSQL.getId());
		}
		if (target != null) {
			query.append(" AND target_id=" + target.getId());
			typeSQL = TypeSQL.getType(target.getEntityType(), con);
			query.append(" AND target_type_id=" + typeSQL.getId());
		}

		if (method != null) {
			query.append(" AND method_id=" + AnnotationMethodSQL.getMethod(method, con).getId());
		}

		if (types != null) {
			query.append(" AND type_id IN (");
			for (Type type : types) {
				typeSQL = TypeSQL.getType(type, con);
				query.append(typeSQL.getId() + ",");
			}
			query.replace(query.length() - 1, query.length(), ")");
		}

		Collection<AnnotationSQL<SO, TA>> ret = new ArrayList<AnnotationSQL<SO, TA>>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ret.add((AnnotationSQL<SO, TA>) EntityFactorySQL.createObject(rs.getInt("annotation_id"),
					getEntityType(con), con));
			}
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
		return ret;
	}

	public static <TA extends EntitySQL> Collection<AnnotationSQL< ? , TA>> getAnnotations(Type sourceType, TA target,
			AnnotationMethod method, Collection<Type> types, Connection con) throws SQLException {
		StringBuffer query = new StringBuffer(
			"SELECT annotation_id FROM Annotation AN, Association AS, Method M, Source_target_type S, Association_type T WHERE");
		query.append(" AN.annotation_id = AS.association_id AND AN.annotation_method_id = M.method_id");
		query.append(" AND AS.association_id=T.association_id AND AS.source_target_type_id=S.source_target_type_id");
		TypeSQL typeSQL;

		if (sourceType != null) {
			typeSQL = TypeSQL.getType(sourceType, con);
			query.append(" AND source_type_id=" + typeSQL.getId());
		}
		if (target != null) {
			query.append(" AND target_id=" + target.getId());
			typeSQL = TypeSQL.getType(target.getEntityType(), con);
			query.append(" AND target_type_id=" + typeSQL.getId());
		}

		if (method != null) {
			query.append(" AND method_id=" + AnnotationMethodSQL.getMethod(method, con).getId());
		}

		if (types != null) {
			query.append(" AND type_id IN (");
			for (Type type : types) {
				typeSQL = TypeSQL.getType(type, con);
				query.append(typeSQL.getId() + ",");
			}
			query.replace(query.length() - 1, query.length(), ")");
		}

		Collection<AnnotationSQL< ? , TA>> ret = new ArrayList<AnnotationSQL< ? , TA>>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ret.add((AnnotationSQL< ? , TA>) EntityFactorySQL.createObject(rs.getInt("annotation_id"),
					getEntityType(con), con));
			}
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
		return ret;
	}

	public static <SO extends EntitySQL> Collection<AnnotationSQL<SO, ? >> getAnnotations(SO source, Type targetType,
			AnnotationMethod method, Collection<Type> types, Connection con) throws SQLException {
		StringBuffer query = new StringBuffer(
			"SELECT annotation_id FROM Annotation AN, Association AS, Method M, Source_target_type S, Association_type T WHERE");
		query.append(" AN.annotation_id = AS.association_id AND AN.annotation_method_id = M.method_id");
		query.append(" AND AS.association_id=T.association_id AND AS.source_target_type_id=S.source_target_type_id");
		TypeSQL typeSQL;

		if (source != null) {
			query.append(" AND source_id=" + source.getId());
			typeSQL = TypeSQL.getType(source.getEntityType(), con);
			query.append(" AND source_type_id=" + typeSQL.getId());
		}
		if (targetType != null) {
			typeSQL = TypeSQL.getType(targetType, con);
			query.append(" AND target_type_id=" + typeSQL.getId());
		}

		if (method != null) {
			query.append(" AND method_id=" + AnnotationMethodSQL.getMethod(method, con).getId());
		}

		if (types != null) {
			query.append(" AND type_id IN (");
			for (Type type : types) {
				typeSQL = TypeSQL.getType(type, con);
				query.append(typeSQL.getId() + ",");
			}
			query.replace(query.length() - 1, query.length(), ")");
		}

		Collection<AnnotationSQL<SO, ? >> ret = new ArrayList<AnnotationSQL<SO, ? >>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ret.add((AnnotationSQL<SO, ? >) EntityFactorySQL.createObject(rs.getInt("annotation_id"),
					getEntityType(con), con));
			}
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
		return ret;
	}

	public static Collection<AnnotationSQL< ? , ? >> getAnnotations(Type sourceType, Type targetType,
			AnnotationMethod method, Collection<Type> types, Connection con) throws SQLException {
		StringBuffer query = new StringBuffer(
			"SELECT annotation_id FROM Annotation AN, Association AS, Method M, Source_target_type S, Association_type T WHERE");
		query.append(" AN.annotation_id = AS.association_id AND AN.annotation_method_id = M.method_id");
		query.append(" AND AS.association_id=T.association_id AND AS.source_target_type_id=S.source_target_type_id");
		TypeSQL typeSQL;

		if (sourceType != null) {
			typeSQL = TypeSQL.getType(sourceType, con);
			query.append(" AND source_type_id=" + typeSQL.getId());
		}
		if (targetType != null) {
			typeSQL = TypeSQL.getType(targetType, con);
			query.append(" AND target_type_id=" + typeSQL.getId());
		}

		if (method != null) {
			query.append(" AND method_id=" + AnnotationMethodSQL.getMethod(method, con).getId());
		}

		if (types != null) {
			query.append(" AND type_id IN (");
			for (Type type : types) {
				typeSQL = TypeSQL.getType(type, con);
				query.append(typeSQL.getId() + ",");
			}
			query.replace(query.length() - 1, query.length(), ")");
		}

		Collection<AnnotationSQL< ? , ? >> ret = new ArrayList<AnnotationSQL< ? , ? >>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ret.add((AnnotationSQL< ? , ? >) EntityFactorySQL.createObject(rs.getInt("annotation_id"),
					getEntityType(con), con));
			}
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException ex) {
					// ignore
				}
			}
		}
		return ret;
	}

}
