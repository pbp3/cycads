/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface AnnotationFinder<X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod, AOT extends AnnotationObjectType>
{
	/* The arguments nulls are not considerated */
	public <A extends Annotation< ? extends SO, ? extends TA, ? , ? extends X, ? extends T, ? extends M>, SO extends AnnotationObject<AOT>, TA extends AnnotationObject<AOT>> Collection< ? extends A> getAnnotations(
			SO source, TA target, M method, Collection<T> types, X synonym);

	/* The arguments nulls are not considerated */
	public <A extends Annotation< ? , ? extends TA, ? , ? extends X, ? extends T, ? extends M>, TA extends AnnotationObject<AOT>> Collection< ? extends A> getAnnotations(
			AOT sourceType, TA target, M method, Collection<T> types, X synonym);

	/* The arguments nulls are not considerated */
	public <A extends Annotation< ? extends SO, ? , ? , ? extends X, ? extends T, ? extends M>, SO extends AnnotationObject<AOT>> Collection< ? extends A> getAnnotations(
			SO source, AOT targetType, M method, Collection<T> types, X synonym);

	/* The arguments nulls are not considerated */
	public <A extends Annotation< ? , ? , ? , ? extends X, ? extends T, ? extends M>> Collection< ? extends A> getAnnotations(
			AOT sourceType, AOT targetType, M method, Collection<T> types, X synonym);

	public <A extends Annotation< ? , ? , ? , ? , ? , ? >> Collection< ? extends A> getAnnotations(
			AnnotationFilter<A> filter);

	//	public Collection< ? extends A> getDbxrefAnnotations(String dbxrefDbname);
	//
}
