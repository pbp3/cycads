/*
 * Created on 15/07/2009
 */
package org.cycads.entities.reaction.SQL;

import org.cycads.entities.reaction.CompoundReaction;

public class CompoundReactionSQL implements CompoundReaction<CompoundSQL, ReactionSQL>
{

	private ReactionSQL	reaction;
	private CompoundSQL	compound;
	private boolean		sideA;
	private int			quantity;

	protected CompoundReactionSQL(CompoundSQL compound, ReactionSQL reaction, boolean sideA, int quantity) {
		this.compound = compound;
		this.reaction = reaction;
		this.quantity = quantity;
		this.sideA = sideA;
	}

	@Override
	public CompoundSQL getCompound() {
		return compound;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public ReactionSQL getReaction() {
		return reaction;
	}

	@Override
	public boolean isSideA() {
		return sideA;
	}

}
