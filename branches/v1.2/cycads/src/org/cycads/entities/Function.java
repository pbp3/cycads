/*
 * Created on 25/02/2009
 */
package org.cycads.entities;

import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.HasSynonyms;

public interface Function extends EntityObject, HasSynonyms, Noteble
{
	public String getName();

	public String getDescription();

}
