/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;

import org.cycads.entities.synonym.Dbxref;

public interface CompoundReaction<X extends Dbxref< ? , ? , ? , ? >, R extends Reaction< ? , ? , ? >>
		extends Compound<X>
{
	public R getReaction();

	public boolean isSideA();

	public int getQuantity();

}
