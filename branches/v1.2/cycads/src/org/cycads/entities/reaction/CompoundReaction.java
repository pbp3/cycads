/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;


public interface CompoundReaction<R extends Reaction< ? , ? , ? , ? >, C extends Compound< ? >>

{
	public C getCompound();

	public R getReaction();

	public boolean isSideA();

	public int getQuantity();

}
