/*
 * Created on 21/10/2009
 */
package org.cycads.entities;

import java.util.Collection;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface EntityFinder<E extends EntityObject>
{
	public Collection<E> getEntitiesBySynonym(Type type, String dbName, String accession);

	public Collection<E> getEntitiesBySynonym(Type type, Dbxref synonym);

}
