/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Organism<S extends Sequence< ? , ? >, SS extends Subsequence< ? >>
		extends Noteble, HasSynonyms, EntityObject
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
	public Collection<S> getSequencesBySynonym(Dbxref synonym, String version);

	//
	//	public Collection<SS> getSubsequences(Dbxref synonym);
	//
	public int getNextCycId();

}