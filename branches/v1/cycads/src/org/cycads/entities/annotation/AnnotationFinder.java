/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface AnnotationFinder<A extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? >, T extends Type, M extends AnnotationMethod>
{
	public Collection<A> getSubseqAnnotations(X synonym);

	public Collection<A> getSubseqAnnotations(T type);

	public Collection<A> getSubseqAnnotations(M method);

	public Collection<A> getSubseqAnnotations(M method, T type);

	public Collection<A> getSubseqAnnotations(AnnotationFilter<A> filter);
}
