/*
 * Created on 21/10/2009
 */
package org.cycads.entities;

import java.util.Collection;

import org.cycads.entities.synonym.Dbxref;

public interface EntityFinder<E extends BasicEntity>
{
	public Collection< ? extends E> getEntitiesBySynonym(String dbName, String accession, String objectType);

	public Collection< ? extends E> getEntitiesBySynonym(Dbxref synonym, String objectType);

}
