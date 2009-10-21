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

import org.cycads.entities.annotation.Association;
import org.cycads.entities.annotation.AssociationFilter;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.note.Type;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class AssociationSQL<SO extends EntitySQL, TA extends EntitySQL> extends HasSynonymsNotebleSQL
		implements Association<SO, TA>, EntitySQL
{

	private int					id;

	/* The types are not synchonized */
	private Collection<TypeSQL>	types;
	private SO					source;
	private TA					target;

	private Connection			con;

	public AssociationSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT source_id, target_id, source_target_type_id from Association WHERE asociation_id=?");
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
			TA target, Connection con, TypeSQL... types) throws SQLException {

		SourceTargetTypeSQL sourceTargetType = new SourceTargetTypeSQL(TypeSQL.getType(source.getEntityType(), con),
			TypeSQL.getType(target.getEntityType(), con), con);

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(
				"INSERT INTO Asociation (source_id, target_id, source_target_type_id) VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, source.getId());
			stmt.setInt(2, target.getId());
			stmt.setInt(3, sourceTargetType.getId());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				AssociationSQL<SO, TA> ret = new AssociationSQL<SO, TA>(rs.getInt(1), con);
				if (types != null) {
					for (TypeSQL type : types) {
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
				stmt = con.prepareStatement("SELECT type_id from Association_type WHERE association_id=?");
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
		TypeSQL typeSQL = TypeSQL.getType(type, con);
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
		TypeSQL typeSQL = TypeSQL.getType(type, con);
		if (isType(typeSQL)) {
			return typeSQL;
		}
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO Association_type (association_id, type_id) VALUES (?,?)");
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
	public int getId() {
		return id;
	}

	@Override
	public Connection getConnection() {
		return con;
	}

	@Override
	public TypeSQL getEntityType() {
		return getEntityType(con);
	}

	public static TypeSQL getEntityType(Connection con) {
		return TypeSQL.getType(TypeSQL.ASSOCIATION, con);
	}

	public static <SO extends EntitySQL, TA extends EntitySQL> Collection<AssociationSQL< ? extends SO, ? extends TA>> getAssociations(
			SO source, TA target, AssociationFilter filter, Connection con, Type... types) throws SQLException {
		StringBuffer query = new StringBuffer(
			"SELECT association_id FROM Association A, Source_target_type S, Association_type T WHERE ");
		query.append("A.association_id=T.association_id=T AND A.source_target_type_id=S.source_target_type_id");
		if (source != null) {
			query.append(" AND source_id=" + source.getId());
			query.append(" AND source_type_id=" + source.getEntityType().getId());
		}
		if (target != null) {
			query.append(" AND target_id=" + target.getId());
		}
		if (types != null) {
			query.append("AND type_id IN (");
			for (Type type : types) {
				TypeSQL typeSQL = TypeSQL.getType(type, con);
				query.append(typeSQL.getId() + ",");
			}
			query.replace(start, end, ")");
		}
		query.append("WHERE 1");
		// TODO Auto-generated method stub
		return null;
	}

	public static <TA extends EntitySQL> Collection<AssociationSQL< ? , ? extends TA>> getAssociations(Type sourceType,
			TA target, AssociationFilter filter, Connection con, Type... types) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public static <SO extends EntitySQL> Collection<AssociationSQL< ? extends SO, ? >> getAssociations(SO source,
			Type targetType, AssociationFilter filter, Connection con, Type... types) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public static Collection<AssociationSQL< ? , ? >> getAssociations(Type sourceType, Type targetType,
			AssociationFilter filter, Connection con, Type... types) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
