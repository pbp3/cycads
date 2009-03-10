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
	public Collection<A> getAnnotations(M method, T type, X synonym);

	public Collection<A> getDbxrefAnnotations(M method, X dbxref);

	public Collection<A> getAnnotations(AnnotationFilter<A> filter);

}
