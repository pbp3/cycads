/*
 * Created on 16/10/2009
 */
package org.cycads.entities;

import org.cycads.entities.annotation.Annotable;
import org.cycads.entities.annotation.Associable;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.HasSynonyms;

public interface EntityObject extends Noteble, HasSynonyms, Associable, Annotable
{
	public Type getEntityType();

}
