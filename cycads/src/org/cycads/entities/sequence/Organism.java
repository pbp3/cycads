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

public interface Organism<S extends Sequence< ? , ? , ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? >, SA extends Annotation<SS, ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends AnnotationFinder<SA, X, X, T, M>
{
	public int getId();

	public String getName();

	public void setName(String name);

	public S createNewSequence(String version);

	public Collection<S> getSequences();

	public Collection<S> getSequences(String version);

	public Collection<S> getSequences(X synonym);

	public Collection<S> getSequences(X synonym, String version);

	public Collection<SS> getSubsequences(X synonym);

	public int getNextCycId();

}