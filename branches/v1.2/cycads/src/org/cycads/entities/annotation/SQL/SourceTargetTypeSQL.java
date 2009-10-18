package org.cycads.entities.annotation.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cycads.entities.note.SQL.TypeSQL;

public class SourceTargetTypeSQL
{
	TypeSQL	sourceType, targetType;
	int		id;

	public SourceTargetTypeSQL(TypeSQL sourceType, TypeSQL targetType, Connection con) throws SQLException {
		this.sourceType = sourceType;
		this.targetType = targetType;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT source_target_type_id from source_target_type WHERE source_type_id=? AND target_type_id=?");
			stmt.setInt(1, sourceType.getId());
			stmt.setInt(2, targetType.getId());
			rs = stmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt("source_target_type_id");
			}
			else {
				stmt = con.prepareStatement(
					"INSERT INTO source_target_type (source_type_id, target_type_id) VALUES (?,?)",
					Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, sourceType.getId());
				stmt.setInt(2, targetType.getId());
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
				else {
					throw new SQLException("SourceTargetTypeSQL insert didn't return the id.");
				}
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

	public SourceTargetTypeSQL(int id, Connection con) throws SQLException {
		this.id = id;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("SELECT source_type_id, target_type_id from source_target_type WHERE source_target_type_id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				sourceType = new TypeSQL(rs.getInt("source_type_id"), con);
				targetType = new TypeSQL(rs.getInt("target_type_id"), con);
			}
			else {
				throw new SQLException("SourceTargetTypeSQL does not exist:" + id);
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

}
