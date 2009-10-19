/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.HasSynonyms;

public interface Pathway<R extends Reaction< ? , ? , ? >> extends Noteble, HasSynonyms, EntityObject
{
	public static final String	OBJECT_TYPE_NAME	= "Pathway";

	public Collection<R> getReactions();

}
