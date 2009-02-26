/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

public interface Dbxref<X extends Dbxref< ? >> extends HasSynonyms<X>
{
	public String getDbName();

	public String getAccession();

}
