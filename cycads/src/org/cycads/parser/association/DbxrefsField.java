/*
 * Created on 29/09/2009
 */
package org.cycads.parser.association;

import java.util.ArrayList;
import java.util.List;

import org.cycads.entities.synonym.Dbxref;

public class DbxrefsField<X extends Dbxref>
{
	List<X>	dbxrefs	= new ArrayList<X>();

	public void add(X dbxref) {
		dbxrefs.add(dbxref);
	}

	public List<X> getListDbxref() {
		return dbxrefs;
	}

}
