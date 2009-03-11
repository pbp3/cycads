/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Sequence<O extends Organism< ? , ? , ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? >, SA extends SubseqAnnotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Noteble, HasSynonyms<X>, AnnotationFinder<SA, X, T, M>
{

	public String getVersion();

	public int getId();

	public O getOrganism();

	public int getLength();

	public String getSequenceString();

	public void setSequenceString(String seqStr);

	public SS getSubsequence(int start, int end, Collection<Intron> introns);

	public SS createSubsequence(int start, int end, Collection<Intron> introns);

	public Collection<SS> getSubsequences(int start);

	public Collection<SS> getSubsequences(X synonym);

}