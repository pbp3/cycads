/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;

import java.util.Collection;

import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Pathway<X extends Dbxref< ? , ? , ? , ? >, R extends Reaction< ? , ? , ? , ? >>
		extends Noteble, HasSynonyms<X>
{
	public Collection<R> getReactions();

}
