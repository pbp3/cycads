/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.note.Noteble;

public interface Dbxref extends Noteble, HasSynonyms, BasicEntity
{
	public static final String	OBJECT_TYPE_NAME	= "Dbxref";

	public String getDbName();

	public String getAccession();

	public Database getDatabase();

}
