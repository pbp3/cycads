/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;

import java.util.Collection;

import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.EC;
import org.cycads.entities.synonym.HasSynonyms;

public interface Reaction<X extends Dbxref< ? , ? , ? , ? >, E extends EC< ? , ? , ? , ? >, C extends CompoundReaction< ? , ? >>
		extends Noteble, HasSynonyms<X>
{
	public E getEC();

	public boolean isAToB();

	public boolean isBToA();

	public Collection<C> getCompounds();

}
