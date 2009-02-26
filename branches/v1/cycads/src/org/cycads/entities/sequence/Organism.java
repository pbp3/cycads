/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface Organism<X extends Dbxref, T extends Type, M extends AnnotationMethod>
{
	public int getId();

	public Collection<Sequence< ? extends X, ? extends T, ? extends M>> getSequences();

	public Collection<Sequence< ? extends X, ? extends T, ? extends M>> getSequences(double version);

	public Sequence< ? extends X, ? extends T, ? extends M> getSequence(int id);

	public Sequence< ? extends X, ? extends T, ? extends M> createNewSequence(double version);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(T type);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(M method);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(M method, T type);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(AnnotationFilter filter);

	public Collection<Sequence< ? extends X, ? extends T, ? extends M>> getSequences(X synonym);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubsequences(X synonym);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(X synonym);

}