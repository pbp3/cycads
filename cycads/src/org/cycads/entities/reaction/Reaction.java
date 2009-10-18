/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;

import java.util.Collection;

import org.cycads.entities.annotation.AssociationObject;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.EC;
import org.cycads.entities.synonym.HasSynonyms;

public interface Reaction<E extends EC, CR extends CompoundReaction< ? , ? >>
		extends Noteble, HasSynonyms, AssociationObject
{
	public static final String	OBJECT_TYPE_NAME	= "Reaction";

	public E getEC();

	public boolean isReversible();

	public Collection<CR> getCompounds();

	public Collection<CR> getCompoundsSideA();

	public Collection<CR> getCompoundsSideB();

	public CR addCompoundSideA(Compound compound, int quantity);

	public CR addCompoundSideB(Compound compound, int quantity);

}
