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
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.note.Type;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.Dbxref;
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
			TA target, TypeSQL type, Connection con) throws SQLException {

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
				if (type != null) {
					ret.addType(type);
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

	//	public static Collection<AssociationSQL> getAnnotations(Dbxref dbxref, Connection con) throws SQLException {
	//		int dbxrefId;
	//		PreparedStatement stmt = null;
	//		ResultSet rs = null;
	//		try {
	//			if (dbxref instanceof DbxrefSQL) {
	//				dbxrefId = ((DbxrefSQL) dbxref).getId();
	//			}
	//			else {
	//				dbxrefId = DbxrefSQL.getId(dbxref.getDbName(), dbxref.getAccession(), con);
	//			}
	//			stmt = con.prepareStatement("SELECT annotation_id from Annotation_synonym WHERE dbxref_id=?");
	//			stmt.setInt(1, dbxrefId);
	//			rs = stmt.executeQuery();
	//			ArrayList<AssociationSQL> ret = new ArrayList<AssociationSQL>();
	//			while (rs.next()) {
	//				ret.add(new AssociationSQL(rs.getInt("annotation_id"), con));
	//			}
	//			return ret;
	//		}
	//		finally {
	//			if (rs != null) {
	//				try {
	//					rs.close();
	//				}
	//				catch (SQLException ex) {
	//					// ignore
	//				}
	//			}
	//			if (stmt != null) {
	//				try {
	//					stmt.close();
	//				}
	//				catch (SQLException ex) {
	//					// ignore
	//				}
	//			}
	//		}
	//	}

	public static <SO extends EntitySQL, TA extends EntitySQL> Collection< ? extends AssociationSQL< ? extends SO, ? extends TA>> getAssociations(
			SO source, TA target, TypeSQL type, Dbxref synonym, Connection con) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Collection<AssociationSQL< ? extends SO, ? extends TA>> ret = new ArrayList<AssociationSQL< ? extends SO, ? extends TA>>();
		try {
			stmt = con.prepareStatement("SELECT target_id, target_type_id from Association WHERE annotation_id=?");
			stmt.setInt(1, getId());
			rs = stmt.executeQuery();
			while (rs.next()) {
				parents.add(EntityFactorySQL.createObject(rs.getInt("target_id"), rs.getInt("target_type_id"),
					getConnection()));
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
		return ret;
	}

	@Override
	public TypeSQL getEntityType() {
		return getObjectType(con);
	}

	public static TypeSQL getObjectType(Connection con) {
		return TypeSQL.getType(TypeSQL.ASSOCIATION, con);
	}

}
