/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import java.util.Collection;

public interface HasSynonyms<X extends Dbxref< ? , ? , ? , ? >>
{
	public Collection< ? extends X> getSynonyms();

	public Collection< ? extends X> getSynonyms(String dbName);

	public X getSynonym(String dbName, String accession);

	/*
	 return the dbxref which was added. If has already existed the dbxref return null.
	 */
	public X addSynonym(String dbName, String accession);

	public void addSynonym(X dbxref);

	public boolean isSynonym(X dbxref);

	public boolean isSynonym(String dbName, String accession);

}
