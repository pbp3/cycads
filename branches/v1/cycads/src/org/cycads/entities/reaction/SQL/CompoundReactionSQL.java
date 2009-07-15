package org.cycads.entities.reaction.SQL;

import java.sql.Connection;

import org.cycads.entities.reaction.CompoundReaction;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class CompoundReactionSQL extends HasSynonymsNotebleSQL implements CompoundReaction<DbxrefSQL, ReactionSQL>
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
	public int getQuantity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReactionSQL getReaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSideA() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSmallMolecule() {
		// TODO Auto-generated method stub
		return false;
	}

}
