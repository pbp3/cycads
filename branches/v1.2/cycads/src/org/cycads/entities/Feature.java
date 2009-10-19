/*
 * Created on 28/09/2009
 */
package org.cycads.entities;

import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.HasSynonyms;

public interface Feature extends EntityObject, HasSynonyms, Noteble
{
	public static final String	OBJECT_TYPE_NAME	= "Feature";

	public String getName();

	public String getDescription();

}
