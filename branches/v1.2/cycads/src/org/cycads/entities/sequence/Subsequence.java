/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.synonym.HasSynonyms;

public interface Subsequence<S extends Sequence< ? , ? >> extends Noteble, HasSynonyms, BasicEntity
{
	public static final String	ENTITY_TYPE_NAME	= "Subsequence";

	public Collection<Intron> getIntrons();

	//	public boolean addIntron(Intron intron);
	//
	//	public boolean removeIntron(Intron intron);
	//
	//	public boolean addExon(int start, int end);
	//
	public boolean isPositiveStrand();

	public int getStart();

	public int getEnd();

	public int getMinPosition();

	public int getMaxPosition();

	public S getSequence();

	public boolean contains(Subsequence< ? > subseq);

}