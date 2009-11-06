/*
 * Created on 04/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.SQL.EntitySQL;
import org.cycads.entities.SQL.SimpleEntitySQL;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.note.Type;
import org.cycads.entities.note.SQL.TypeSQL;

public class AssociationSQL<SO extends EntitySQL, TA extends EntitySQL> extends SimpleEntitySQL
		implements Association<SO, TA>
{

	/* The types are not synchonized */
	private Collection<TypeSQL>	types;
	private SO					source;
	private TA					target;

	public AssociationSQL(int id, Connection con) throws SQLException {
		super(id, con);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT source_id, target_id, source_target_type_id FROM Association WHERE association_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				SourceTargetTypeSQL sourceTargetType = new SourceTargetTypeSQL(rs.getInt("source_target_type_id"), con);
				source = (SO) EntityFactorySQL.createObject(rs.getInt("source_id"), sourceTargetType.getSourceType(),
					con);
				target = (TA) EntityFactorySQL.createObject(rs.getInt("target_id"), sourceTargetType.getTargetType(),
					con);
			}
			else {
				throw new SQLException("Association does not exist:" + id);
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

	public static <SO extends EntitySQL, TA extends EntitySQL> AssociationSQL<SO, TA> createAssociationSQL(SO source,
			TA target, Collection< ? extends Type> types, Connection con) throws SQLException {

		SourceTargetTypeSQL sourceTargetType = new SourceTargetTypeSQL(TypeSQL.getType(source.getEntityType(), con),
			TypeSQL.getType(target.getEntityType(), con), con);

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(
				"INSERT INTO Association (source_id, target_id, source_target_type_id) VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, source.getId());
			stmt.setInt(2, target.getId());
			stmt.setInt(3, sourceTargetType.getId());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				AssociationSQL<SO, TA> ret = new AssociationSQL<SO, TA>(rs.getInt(1), con);
				if (types != null) {
					for (Type type : types) {
						ret.addType(type);
					}
				}
				return ret;
			}
			else {
				throw new SQLException("Association insert didn't return the association id.");
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

	@Override
	public SO getSource() {
		return source;
	}

	@Override
	public TA getTarget() {
		return target;
	}

	@Override
	public Collection<TypeSQL> getTypes() {
		if (types == null) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			types = new ArrayList<TypeSQL>();
			try {
				stmt = getConnection().prepareStatement("SELECT type_id FROM Association_type WHERE association_id=?");
				stmt.setInt(1, getId());
				rs = stmt.executeQuery();
				while (rs.next()) {
					types.add(TypeSQL.getType(rs.getInt("type_id"), getConnection()));
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
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
		return types;
	}

	@Override
	public boolean isType(String typeStr) {
		for (TypeSQL type : getTypes()) {
			if (type.getName().equals(typeStr)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isType(Type type) {
		TypeSQL typeSQL = TypeSQL.getType(type, getConnection());
		for (TypeSQL type1 : getTypes()) {
			if (type1.equals(typeSQL)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TypeSQL addType(String typeStr) {
		TypeSQL type = TypeSQL.getType(typeStr, getConnection());
		return addType(type);
	}

	@Override
	public TypeSQL addType(Type type) {
		TypeSQL typeSQL = TypeSQL.getType(type, getConnection());
		if (isType(typeSQL)) {
			return typeSQL;
		}
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement(
				"INSERT INTO Association_type (association_id, type_id) VALUES (?,?)");
			stmt.setInt(1, getId());
			stmt.setInt(2, typeSQL.getId());
			stmt.executeUpdate();
			types.add(typeSQL);
			return typeSQL;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
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
	public String getEntityTypeName() {
		return Association.ENTITY_TYPE_NAME;
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(Association.ENTITY_TYPE_NAME, con);
	}

	public static <SO extends EntitySQL, TA extends EntitySQL> Collection<AssociationSQL<SO, TA>> getAssociations(
			SO source, TA target, Collection< ? extends Type> types, Connection con) throws SQLException {
		StringBuffer query = new StringBuffer(
			"SELECT distinct(A.association_id) FROM Association A, Source_target_type S");
		if (types != null && types.size() > 0) {
			query.append(", Association_type T");
		}
		query.append(" WHERE A.source_target_type_id=S.source_target_type_id");
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
		if (types != null && types.size() > 0) {
			query.append(" AND A.association_id=T.association_id");
			if (types.size() == 1) {
				typeSQL = TypeSQL.getType(types.iterator().next(), con);
				query.append(" AND type_id=" + typeSQL.getId());
			}
			else {
				query.append(" AND type_id IN (");
				for (Type type : types) {
					typeSQL = TypeSQL.getType(type, con);
					query.append(typeSQL.getId() + ",");
				}
				query.replace(query.length() - 1, query.length(), ")");
			}
		}
		Collection<AssociationSQL<SO, TA>> ret = new ArrayList<AssociationSQL<SO, TA>>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ret.add((AssociationSQL<SO, TA>) EntityFactorySQL.createObject(rs.getInt("association_id"),
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

	public static <TA extends EntitySQL> Collection<AssociationSQL< ? , TA>> getAssociations(Type sourceType,
			TA target, Collection< ? extends Type> types, Connection con) throws SQLException {
		StringBuffer query = new StringBuffer(
			"SELECT distinct(A.association_id) FROM Association A, Source_target_type S");
		if (types != null && types.size() > 0) {
			query.append(", Association_type T");
		}
		query.append(" WHERE A.source_target_type_id=S.source_target_type_id");
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
		if (types != null && types.size() > 0) {
			query.append(" AND A.association_id=T.association_id");
			if (types.size() == 1) {
				typeSQL = TypeSQL.getType(types.iterator().next(), con);
				query.append(" AND type_id=" + typeSQL.getId());
			}
			else {
				query.append(" AND type_id IN (");
				for (Type type : types) {
					typeSQL = TypeSQL.getType(type, con);
					query.append(typeSQL.getId() + ",");
				}
				query.replace(query.length() - 1, query.length(), ")");
			}
		}
		Collection<AssociationSQL< ? , TA>> ret = new ArrayList<AssociationSQL< ? , TA>>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ret.add((AssociationSQL< ? , TA>) EntityFactorySQL.createObject(rs.getInt("association_id"),
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

	public static <SO extends EntitySQL> Collection<AssociationSQL<SO, ? >> getAssociations(SO source, Type targetType,
			Collection< ? extends Type> types, Connection con) throws SQLException {
		StringBuffer query = new StringBuffer(
			"SELECT distinct(A.association_id) FROM Association A, Source_target_type S");
		if (types != null && types.size() > 0) {
			query.append(", Association_type T");
		}
		query.append(" WHERE A.source_target_type_id=S.source_target_type_id");
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
		if (types != null && types.size() > 0) {
			query.append(" AND A.association_id=T.association_id");
			if (types.size() == 1) {
				typeSQL = TypeSQL.getType(types.iterator().next(), con);
				query.append(" AND type_id=" + typeSQL.getId());
			}
			else {
				query.append(" AND type_id IN (");
				for (Type type : types) {
					typeSQL = TypeSQL.getType(type, con);
					query.append(typeSQL.getId() + ",");
				}
				query.replace(query.length() - 1, query.length(), ")");
			}
		}
		Collection<AssociationSQL<SO, ? >> ret = new ArrayList<AssociationSQL<SO, ? >>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ret.add((AssociationSQL<SO, ? >) EntityFactorySQL.createObject(rs.getInt("association_id"),
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

	public static Collection<AssociationSQL< ? , ? >> getAssociations(Type sourceType, Type targetType,
			Collection< ? extends Type> types, Connection con) throws SQLException {
		StringBuffer query = new StringBuffer(
			"SELECT distinct(A.association_id) FROM Association A, Source_target_type S");
		if (types != null && types.size() > 0) {
			query.append(", Association_type T");
		}
		query.append(" WHERE A.source_target_type_id=S.source_target_type_id");
		TypeSQL typeSQL;
		if (sourceType != null) {
			typeSQL = TypeSQL.getType(sourceType, con);
			query.append(" AND source_type_id=" + typeSQL.getId());
		}
		if (targetType != null) {
			typeSQL = TypeSQL.getType(targetType, con);
			query.append(" AND target_type_id=" + typeSQL.getId());
		}
		if (types != null && types.size() > 0) {
			query.append(" AND A.association_id=T.association_id");
			if (types.size() == 1) {
				typeSQL = TypeSQL.getType(types.iterator().next(), con);
				query.append(" AND type_id=" + typeSQL.getId());
			}
			else {
				query.append(" AND type_id IN (");
				for (Type type : types) {
					typeSQL = TypeSQL.getType(type, con);
					query.append(typeSQL.getId() + ",");
				}
				query.replace(query.length() - 1, query.length(), ")");
			}
		}
		Collection<AssociationSQL< ? , ? >> ret = new ArrayList<AssociationSQL< ? , ? >>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(query.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				ret.add((AssociationSQL< ? , ? >) EntityFactorySQL.createObject(rs.getInt("association_id"),
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
