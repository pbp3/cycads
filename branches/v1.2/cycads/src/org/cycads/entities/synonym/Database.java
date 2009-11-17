/*
 * Created on 28/09/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.note.Noteble;

public interface Database extends BasicEntity, HasSynonyms, Noteble
{
	public static final String	OBJECT_TYPE_NAME	= "External_DB";

	public String getName();

	public String getDescription();

}
