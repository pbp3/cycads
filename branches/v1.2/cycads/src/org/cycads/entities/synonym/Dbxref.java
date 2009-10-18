/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.annotation.AssociationObject;
import org.cycads.entities.note.Noteble;
import org.cycads.extract.cyc.CycDBLink;

public interface Dbxref extends Noteble, HasSynonyms, AssociationObject, CycDBLink
{
	public String getDbName();

	public String getAccession();

}
