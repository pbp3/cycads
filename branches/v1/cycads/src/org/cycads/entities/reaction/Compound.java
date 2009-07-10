/*
 * Created on 10/07/2009
 */
package org.cycads.entities.reaction;

import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Compound<X extends Dbxref< ? , ? , ? , ? >> extends Noteble, HasSynonyms<X>
{
	public boolean isSmallMolecule();
}
