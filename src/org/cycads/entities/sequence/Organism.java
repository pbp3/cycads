/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.synonym.Dbxref;

public interface Organism<S extends Sequence< ? , ? >> extends BasicEntity
{
	public static final String	ENTITY_TYPE_NAME	= "Organism";

	public int getId();

	public String getName();

	public void setName(String name);

	public S createNewSequence(String version);

	public Collection<S> getSequences();

	public Collection<S> getSequences(String version);

	//	public Collection<S> getSequences(Dbxref synonym);
	//
	public Collection<S> getSequences(Dbxref synonym);

	//
	//	public Collection<SS> getSubsequences(Dbxref synonym);
	//
	public int getNextCycId();

}