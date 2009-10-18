/*
 * Created on 28/09/2009
 */
package org.cycads.entities.factory;

import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.EC;
import org.cycads.entities.synonym.KO;

public interface EntityDbxrefFactory<X extends Dbxref>
{
	public X getDbxref(String dbName, String accession);

	public KO getKO(String ko);

	public EC getEC(String ecNumber);

}
