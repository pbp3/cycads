/*
 * Created on 03/03/2009
 */
package org.cycads.entities.sequence.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.annotation.SQL.SubseqAnnotationSQL;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.SQL.DbxrefSQL;

public class OrganismSQL
		implements Organism<SequenceSQL, SubsequenceSQL, SubseqAnnotationSQL, DbxrefSQL, TypeSQL, AnnotationMethodSQL>
{
	private int			id;
	private String		name;
	private Connection	con;

	public OrganismSQL(int id, Connection con) throws SQLException {
		this.id = id;
		this.con = con;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT name from Organism WHERE NCBI_TAXON_ID=" + id);
			if (rs.next()) {
				name = rs.getString("name");
			}
			else {
				throw new SQLException("Organism does not exist:" + id);
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

	public static OrganismSQL createOrganism(int id, String name, Connection con) throws SQLException {
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			if (name == null) {
				stmt.executeUpdate("INSERT INTO Organism (NCBI_TAXON_ID) VALUES (" + id + ")");
			}
			else {
				stmt.executeUpdate("INSERT INTO Organism (NCBI_TAXON_ID, name) VALUES (" + id + ",'" + name + "')");
			}
			return new OrganismSQL(id, con);
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
	public String getName() {
		return name;
	}

	public Connection getConnection() {
		return con;
	}

	@Override
	public void setName(String name) {
		if (name == null) {
			name = "";
		}
		if (!name.equals(getName())) {
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				stmt.executeUpdate("UPDATE Organism SET name='" + name + "' WHERE NCBI_TAXON_ID=" + getId());
				this.name = name;
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
	public SequenceSQL createNewSequence(String version) {
		int id = 0;

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO sequence (NCBI_TAXON_ID, version) VALUES (" + getId() + ",'" + version
				+ "')", Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			else {
				throw new SQLException("Sequence insert didn't return the id.");
			}
			return new SequenceSQL(id, getConnection());
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
	public Collection<SequenceSQL> getSequences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SequenceSQL> getSequences(String version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SequenceSQL> getSequences(DbxrefSQL synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubsequenceSQL> getSubsequences(DbxrefSQL synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(DbxrefSQL synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(TypeSQL type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(AnnotationMethodSQL method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(AnnotationMethodSQL method, TypeSQL type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SubseqAnnotationSQL> getSubseqAnnotations(AnnotationFilter<SubseqAnnotationSQL> filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
