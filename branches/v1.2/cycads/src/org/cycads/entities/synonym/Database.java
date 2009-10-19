/*
 * Created on 28/09/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Noteble;

public interface Database extends EntityObject, HasSynonyms, Noteble
{
	public static final String	OBJECT_TYPE_NAME	= "Database";

	public String getName();

	public String getDescription();

}
