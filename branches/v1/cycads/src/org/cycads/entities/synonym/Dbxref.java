/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import org.cycads.entities.note.Noteble;

public interface Dbxref<X extends Dbxref< ? >> extends Noteble, HasSynonyms<X>
{
	public String getDbName();

	public String getAccession();

}
