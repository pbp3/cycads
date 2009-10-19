/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Noteble;
import org.cycads.extract.cyc.CycDBLink;

public interface Dbxref extends Noteble, HasSynonyms, EntityObject, CycDBLink
{
	public static final String	OBJECT_TYPE_NAME	= "Dbxref";

	public String getDbName();

	public String getAccession();

}
