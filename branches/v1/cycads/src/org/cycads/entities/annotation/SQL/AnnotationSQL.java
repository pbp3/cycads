/*
 * Created on 04/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public abstract class AnnotationSQL<AParent extends Annotation< ? , ? , ? , ? >> extends HasSynonymsNotebleSQL
		implements Annotation<AParent, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	public final static int		INVALID_ID_PARENT	= 0;

	private int					id;
	private int					parentId			= INVALID_ID_PARENT;
	private int					typeId;
	private int					methodId;

	private AParent				parent				= null;
	private TypeSQL				type;
	private AnnotationMethodSQL	method;

	private Connection			con;

	public AnnotationSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT annotation_parent, type, method from Annotation WHERE annotation_id=" + id);
			if (rs.next()) {
				parentId = rs.getInt("annotation_parent");
				typeId = rs.getInt("type");
				methodId = rs.getInt("method");
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

	public static int createAnnotationSQL(AnnotationSQL< ? > parent, TypeSQL type, AnnotationMethodSQL method,
			Connection con) throws SQLException {
		int id = 0;

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			if (parent == null) {
				stmt.executeUpdate("INSERT INTO Annotation (type, method) VALUES (" + type.getId() + ","
					+ method.getId() + ")", Statement.RETURN_GENERATED_KEYS);
			}
			else {
				stmt.executeUpdate("INSERT INTO Annotation (annotation_parent, type, method) VALUES (" + parent.getId()
					+ "," + type.getId() + "," + method.getId() + ")", Statement.RETURN_GENERATED_KEYS);
			}
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
	public AParent getParent() {
		if (parent == null && getParentId() != INVALID_ID_PARENT) {
			try {
				parent = createParent();
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return parent;
	}

	@Override
	public TypeSQL getType() {
		if (type == null) {
			try {
				type = new TypeSQL(getTypeId(), getConnection());
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return type;
	}

	protected abstract AParent createParent() throws SQLException;

	@Override
	public int getId() {
		return id;
	}

	public int getParentId() {
		return parentId;
	}

	public int getTypeId() {
		return typeId;
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

}
