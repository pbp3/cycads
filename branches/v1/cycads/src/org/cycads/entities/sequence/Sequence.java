/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public interface Sequence<X extends Dbxref, T extends Type, M extends AnnotationMethod> extends Noteble, HasSynonyms
{

	public double getVersion();

	public int getId();

	public Organism< ? extends X, ? extends T, ? extends M> getOrganism();

	public int getLength();

	public String getSequenceString();

	public Subsequence< ? extends X, ? extends T, ? extends M> getSubsequence(int start, int end,
			Collection<Intron> introns);

	public Subsequence< ? extends X, ? extends T, ? extends M> createSubsequence(int start, int end,
			Collection<Intron> introns);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubsequences(int start);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubsequences(X synonym);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(T type);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(M method);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(M method, T type);

	public Collection<Subsequence< ? extends X, ? extends T, ? extends M>> getSubseqAnnotations(AnnotationFilter filter);

}