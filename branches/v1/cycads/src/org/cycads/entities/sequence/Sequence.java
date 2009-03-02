/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Sequence<O extends Organism< ? , ? , ? >, S extends Subsequence< ? , ? , ? , ? >, A extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? >, T extends Type, M extends AnnotationMethod>
		extends Noteble, HasSynonyms<X>, AnnotationFinder<A, X, T, M>
{

	public double getVersion();

	public int getId();

	public O getOrganism();

	public int getLength();

	public String getSequenceString();

	public S getSubsequence(int start, int end, Collection<Intron> introns);

	public S createSubsequence(int start, int end, Collection<Intron> introns);

	public Collection<S> getSubsequences(int start);

	public Collection<S> getSubsequences(X synonym);

}