/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface Organism<S extends Sequence< ? , ? , ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? >, A extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? >, T extends Type, M extends AnnotationMethod>
		extends AnnotationFinder<A, X, T, M>
{
	public int getId();

	public String getName();

	public void setName(String name);

	public Collection<S> getSequences();

	public Collection<S> getSequences(double version);

	public S getSequence(int id);

	public S createNewSequence(double version);

	public Collection<S> getSequences(X synonym);

	public Collection<SS> getSubsequences(X synonym);

}