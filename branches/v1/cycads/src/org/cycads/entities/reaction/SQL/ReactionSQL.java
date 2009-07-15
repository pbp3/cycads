package org.cycads.entities.reaction.SQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.reaction.Reaction;
import org.cycads.entities.synonym.SQL.DbxrefSQL;
import org.cycads.entities.synonym.SQL.ECSQL;
import org.cycads.entities.synonym.SQL.HasSynonymsNotebleSQL;

public class ReactionSQL extends HasSynonymsNotebleSQL
		implements Reaction<DbxrefSQL, ECSQL, CompoundReactionSQL, CompoundSQL>
{
	Connection						con;
	int								id;
	ECSQL							ec;
	boolean							isReversible;
	Collection<CompoundReactionSQL>	compounds;

	@Override
	public String getSynonymTableName() {
		return "reaction_synonym";
	}

	@Override
	public Connection getConnection() {
		return con;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getIdFieldName() {
		return "reaction_id";
	}

	@Override
	public String getNoteTableName() {
		return "reaction_note";
	}

	@Override
	public CompoundReactionSQL addCompoundSideA(CompoundSQL compound, int quantity) {
		Collection<CompoundReactionSQL> comps = getCompounds();
		for (CompoundReactionSQL compR : comps) {
			if (compR.getId() == compound.getId()) {
				if (!compR.isSideA() || compR.getQuantity() != quantity) {
					throw new SQLException("Compound already exists with different quantity or side.");
				}
				else {
					return new CompoundReactionSQL(this, compound, true, quantity);
				}
			}
		}
		addCompound(compound, true, quantity);
		return new CompoundReactionSQL(this, compound, true, quantity);
	}

	@Override
	public CompoundReactionSQL addCompoundSideB(CompoundSQL compound, int quantity) {
		return new CompoundReactionSQL(this, compound, false, quantity);
	}

	@Override
	public Collection<CompoundReactionSQL> getCompounds() {
		return null;
	}

	@Override
	public Collection<CompoundReactionSQL> getCompoundsSideA() {
		Collection<CompoundReactionSQL> result = new ArrayList<CompoundReactionSQL>();
		Collection<CompoundReactionSQL> comps = getCompounds();
		for (CompoundReactionSQL comp : comps) {
			if (comp.isSideA()) {
				result.add(comp);
			}
		}
		return result;
	}

	@Override
	public Collection<CompoundReactionSQL> getCompoundsSideB() {
		Collection<CompoundReactionSQL> result = new ArrayList<CompoundReactionSQL>();
		Collection<CompoundReactionSQL> comps = getCompounds();
		for (CompoundReactionSQL comp : comps) {
			if (!comp.isSideA()) {
				result.add(comp);
			}
		}
		return result;
	}

	@Override
	public ECSQL getEC() {
		return ec;
	}

	@Override
	public boolean isReversible() {
		return isReversible;
	}

}
