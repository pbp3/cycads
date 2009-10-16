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

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;
import org.cycads.general.ParametersDefault;

public class AnnotationSQL<SO extends AnnotationObjectSQL, TA extends AnnotationObjectSQL>
		extends HasSynonymsNotebleSQL
		implements Annotation<SO, TA, AnnotationSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{

	public static String				ScoreNoteTypeName	= ParametersDefault.getScoreAnnotationNoteTypeName();

	private int							id;
	private int							methodId;

	private String						score;

	/* The types are not synchonized */
	private Collection<TypeSQL>			types;
	private AnnotationMethodSQL			method;
	private Collection<AnnotationSQL>	parents;

	private Connection					con;

	public AnnotationSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT annotation_method_id, score from Annotation WHERE annotation_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				methodId = rs.getInt("annotation_method_id");
				score = rs.getString("score");
				sourceId=
					targetId=
						sourceTargetTypeId=
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

	public static int createAnnotationSQL(AnnotationMethodSQL method, Connection con) throws SQLException {
		int id = 0;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("INSERT INTO Annotation (annotation_method_id) VALUES (?)",
				Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, method.getId());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			else {
				throw new SQLException("Annotation insert didn't return the annotation id.");
			}
			return id;
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
		if (method == null) {
			try {
				method = new AnnotationMethodSQL(getMethodId(), getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return method;
	}

	@Override
	public Collection<TypeSQL> getTypes() {
		if (types == null) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			types = new ArrayList<TypeSQL>();
			try {
				stmt = con.prepareStatement("SELECT type_id from Annotation_type WHERE annotation_id=?");
				stmt.setInt(1, getId());
				rs = stmt.executeQuery();
				while (rs.next()) {
					types.add(new TypeSQL(rs.getInt("type_id"), getConnection()));
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
	public boolean hasType(String typeStr) {
		for (TypeSQL type : getTypes()) {
			if (type.getName().equals(typeStr)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TypeSQL addType(String typeStr) {
		try {
			TypeSQL type = new TypeSQL(typeStr, null, getConnection());
			return addType(type);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypeSQL addType(TypeSQL type) {
		PreparedStatement stmt = null;
		try {
			if (!hasType(type.getName())) {
				stmt = con.prepareStatement("INSERT INTO Annotation_type (annotation_id, type_id) VALUES (?,?)");
				stmt.setInt(1, getId());
				stmt.setInt(2, type.getId());
				stmt.executeUpdate();
			}
			if (types != null) {
				types.add(type);
			}
			return type;
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

	public int getMethodId() {
		return methodId;
	}

	@Override
	public String getSynonymTableName() {
		return "Annotation_synonym";
	}

	@Override
	public Connection getConnection() {
		return con;
	}

	@Override
	public String getIdFieldName() {
		return "annotation_id";
	}

	@Override
	public String getNoteTableName() {
		return "Annotation_note";
	}

	@Override
	public void addParent(AnnotationSQL parent) {
		if (!isParent(parent)) {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("INSERT INTO annotation_parent (annotation_id, annotation_parent_id) VALUES (?,?)");
				stmt.setInt(1, getId());
				stmt.setInt(2, parent.getId());
				stmt.executeUpdate();
				if (parents != null) {
					parents.add(parent);
				}
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
	}

	@Override
	public Collection<AnnotationSQL> getParents() {
		if (parents == null) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			parents = new ArrayList<AnnotationSQL>();
			try {
				stmt = con.prepareStatement("SELECT annotation_parent_id from annotation_parent WHERE annotation_id=?");
				stmt.setInt(1, getId());
				rs = stmt.executeQuery();
				while (rs.next()) {
					parents.add(new SubseqAnnotationSQL(rs.getInt("annotation_parent_id"), getConnection()));
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
		return parents;
	}

	public boolean isParent(AnnotationSQL parent) {
		for (AnnotationSQL parent1 : getParents()) {
			if (parent.getId() == parent1.getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getScore() {
		return getNoteValue(ScoreNoteTypeName);
	}

	@Override
	public void setScore(String score) {
		setNoteValue(ScoreNoteTypeName, score);
	}

	public static Collection<AnnotationSQL> getAnnotations(Dbxref dbxref, Connection con) throws SQLException {
		int dbxrefId;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			if (dbxref instanceof DbxrefSQL) {
				dbxrefId = ((DbxrefSQL) dbxref).getId();
			}
			else {
				dbxrefId = DbxrefSQL.getId(dbxref.getDbName(), dbxref.getAccession(), con);
			}
			stmt = con.prepareStatement("SELECT annotation_id from Annotation_synonym WHERE dbxref_id=?");
			stmt.setInt(1, dbxrefId);
			rs = stmt.executeQuery();
			ArrayList<AnnotationSQL> ret = new ArrayList<AnnotationSQL>();
			while (rs.next()) {
				ret.add(new AnnotationSQL(rs.getInt("annotation_id"), con));
			}
			return ret;
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
}
