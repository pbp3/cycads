/*
 * Created on 11/11/2008
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

public interface Subsequence<SA extends SubseqAnnotation< ? , ? extends X, ? extends T, ? extends M>, X extends Dbxref< ? >, T extends Type, M extends AnnotationMethod>
		extends Noteble, HasSynonyms<X>, AnnotationFinder<SA, X, T, M>
{

	public Collection<Intron> getIntrons();

	public boolean isPositiveStrand();

	public int getStart();

	public int getEnd();

	public int getMinPosition();

	public int getMaxPosition();

	public Sequence< ? extends X, ? extends T, ? extends M> getSequence();

	public boolean contains(Subsequence< ? , ? extends X, ? extends T, ? extends M> subseq);

}