package org.cycads.entities.reaction.SQL;

import java.sql.Connection;
import java.util.Collection;

import org.cycads.entities.reaction.Reaction;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.ECSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class ReactionSQL extends HasSynonymsNotebleSQL implements Reaction<DbxrefSQL, ECSQL, CompoundReactionSQL>
{

	@Override
	public String getSynonymTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getIdFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNoteTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAToB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBToA() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<CompoundReactionSQL> getCompounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ECSQL getEC() {
		// TODO Auto-generated method stub
		return null;
	}

}
