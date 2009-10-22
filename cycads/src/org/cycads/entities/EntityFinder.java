/*
 * Created on 21/10/2009
 */
package org.cycads.entities;

import java.util.Collection;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface EntityFinder<E extends EntityObject>
{
	public Collection<E> getEntitiesBySynonym(String dbName, String accession, Type type, EntityFilter<E> filter);

	public Collection<E> getEntitiesBySynonym(Dbxref synonym, Type type, EntityFilter<E> filter);

}
