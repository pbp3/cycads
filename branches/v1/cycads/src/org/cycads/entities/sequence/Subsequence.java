/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Subsequence<X extends Dbxref, T extends Type, M extends AnnotationMethod> extends Noteble, HasSynonyms
{

	public Collection<Intron> getIntrons();

	public boolean isPositiveStrand();

	public int getStart();

	public int getEnd();

	public int getMinPosition();

	public int getMaxPosition();

	public Sequence< ? extends X, ? extends T, ? extends M> getSequence();

	public boolean contains(Subsequence< ? extends X, ? extends T, ? extends M> subseq);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(X synonym);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(T type);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(M method);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(M method, T type);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(AnnotationFilter filter);

}