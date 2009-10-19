/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.HasSynonyms;

public interface Sequence<O extends Organism< ? , ? >, SS extends Subsequence< ? >>
		extends Noteble, HasSynonyms, EntityObject
{
	public static final String	OBJECT_TYPE_NAME	= "Sequence";

	public String getVersion();

	public int getId();

	public O getOrganism();

	public int getLength();

	public String getSequenceString();

	public void setSequenceString(String seqStr);

	public SS getSubsequence(int start, int end, Collection<Intron> introns);

	public SS createSubsequence(int start, int end, Collection<Intron> introns);

	public Collection<SS> getSubsequences(int start);

	//	public Collection<SS> getSubsequences(Dbxref synonym);
	//
}