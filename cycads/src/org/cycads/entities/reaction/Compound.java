/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.HasSynonyms;

public interface Compound extends Noteble, HasSynonyms, EntityObject
{
	public boolean isSmallMolecule();
}
