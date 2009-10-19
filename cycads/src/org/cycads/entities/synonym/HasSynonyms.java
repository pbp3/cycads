/*
 * Created on 24/02/2009
 */
package org.cycads.entities.synonym;

import java.util.Collection;

import org.cycads.entities.EntityObject;

public interface HasSynonyms extends EntityObject
{
	public Collection< ? extends Dbxref> getSynonyms();

	public Collection< ? extends Dbxref> getSynonyms(String dbName);

	public Dbxref getSynonym(String dbName, String accession);

	/*
	 return the dbxref which was added. If has already existed the dbxref return null.
	 */
	public Dbxref addSynonym(String dbName, String accession);

	public void addSynonym(Dbxref dbxref);

	public boolean isSynonym(Dbxref dbxref);

	public boolean isSynonym(String dbName, String accession);

}
