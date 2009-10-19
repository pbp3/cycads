/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.SQL.TypeSQL;
import org.cycads.entities.synonym.EC;
import org.cycads.entities.synonym.HasSynonyms;

public interface Reaction<C extends Compound, E extends EC, CR extends CompoundReaction< ? , ? >>
		extends Noteble, HasSynonyms, EntityObject
{
	public static final String	OBJECT_TYPE_NAME	= TypeSQL.REACTION;

	public E getEC();

	public boolean isReversible();

	public Collection<CR> getCompounds();

	public Collection<CR> getCompoundsSideA();

	public Collection<CR> getCompoundsSideB();

	public CR addCompoundSideA(C compound, int quantity);

	public CR addCompoundSideB(C compound, int quantity);

}
