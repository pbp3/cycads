/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface AnnotationFinder<SA extends SubseqAnnotation< ? , ? , ? , ? , ? >, X extends Dbxref< ? >, T extends Type, M extends AnnotationMethod>
{
	public Collection<SA> getSubseqAnnotations(X synonym);

	public Collection<SA> getSubseqAnnotations(T type);

	public Collection<SA> getSubseqAnnotations(M method);

	public Collection<SA> getSubseqAnnotations(M method, T type);

	public Collection<SA> getSubseqAnnotations(AnnotationFilter<SA> filter);
}
