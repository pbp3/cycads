/*
 * Created on 15/07/2009
 */
package org.cycads.entities.reaction.SQL;

import org.cycads.entities.reaction.CompoundReaction;

public class CompoundReactionSQL implements CompoundReaction<ReactionSQL, CompoundSQL>
{

	private ReactionSQL	reaction;
	private CompoundSQL	compound;
	private boolean		sideA;
	private int			quantity;

	protected CompoundReactionSQL(ReactionSQL reaction, CompoundSQL compound, boolean sideA, int quantity) {
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
