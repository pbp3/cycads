/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface AnnotationFinder<A extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
{
	/* The arguments (method, type or synonym) null are not considerated */
	public Collection< ? extends A> getAnnotations(M method, Collection<T> types, X synonym);

	public Collection< ? extends A> getDbxrefAnnotations(M method, X dbxref);

	public Collection< ? extends A> getAnnotations(AnnotationFilter<A> filter);

}
