/*
 * Created on 04/03/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.SQL.TypeSQL;

public class AnnotationSQL<SO extends EntitySQL, TA extends EntitySQL> extends AssociationSQL<SO, TA>
		implements Annotation<SO, TA>, EntitySQL
{
	public final static String			PARENT_TYPE_NAME	= "Parent";

	private String						score;

	private AnnotationMethodSQL			method;
	private Collection<AnnotationSQL>	parents;

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
			TA target, AnnotationMethodSQL method, String score, Connection con) throws SQLException {

		AssociationSQL<SO, TA> association = AssociationSQL.createAssociationSQL(source, target, con);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("INSERT INTO Annotation (annotation_id, annotation_method_id, score) VALUES (?)");
			stmt.setInt(1, association.getId());
			stmt.setInt(2, method.getId());
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
	public void addParent(Annotation parent) {
		if (!(parent instanceof AnnotationSQL)) {
			throw new RuntimeException("Parent is not a SQL entity.");
		}
		AnnotationSQL parentSQL = (AnnotationSQL) parent;
		if (!isParent(parentSQL)) {
			try {
				AssociationSQL.createAssociationSQL(this, parentSQL, getConnection());
			}
			catch (SQLException e) {
				throw new RuntimeException("Can not create the parent association.", e);
			}
			parents.add(parentSQL);
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

	//	public static Collection<AnnotationSQL> getAnnotations(Dbxref dbxref, Connection con) throws SQLException {
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
	//			ArrayList<AnnotationSQL> ret = new ArrayList<AnnotationSQL>();
	//			while (rs.next()) {
	//				ret.add(new AnnotationSQL(rs.getInt("annotation_id"), con));
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

	@Override
	public TypeSQL getEntityType() {
		return getObjectType(getConnection());
	}

	public static TypeSQL getObjectType(Connection con) {
		return TypeSQL.getType(OBJECT_TYPE_NAME, con);
	}

	public TypeSQL getParentType() {
		return getParentType(getConnection());
	}

	public static TypeSQL getParentType(Connection con) {
		return TypeSQL.getType(PARENT_TYPE_NAME, con);
	}

}
