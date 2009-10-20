/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface AnnotationFinder
{
	/* The arguments nulls are not considerated */
	public <SO extends EntityObject, TA extends EntityObject> Collection< ? extends Annotation< ? extends SO, ? extends TA>> getAnnotations(
			SO source, TA target, AnnotationMethod method, Collection<Type> types, Dbxref synonym,
			AnnotationFilter filter);

	/* The arguments nulls are not considerated */
	public <TA extends EntityObject> Collection< ? extends Annotation< ? , ? extends TA>> getAnnotations(
			Type sourceType, TA target, AnnotationMethod method, Collection<Type> types, Dbxref synonym,
			AnnotationFilter filter);

	/* The arguments nulls are not considerated */
	public <SO extends EntityObject> Collection< ? extends Annotation< ? extends SO, ? >> getAnnotations(SO source,
			Type targetType, AnnotationMethod method, Collection<Type> types, Dbxref synonym, AnnotationFilter filter);

	/* The arguments nulls are not considerated */
	public Collection< ? extends Annotation< ? , ? >> getAnnotations(Type sourceType, Type targetType,
			AnnotationMethod method, Collection<Type> types, Dbxref synonym, AnnotationFilter filter);

	//	public Collection< ? extends A> getDbxrefAnnotations(String dbxrefDbname);
	//
}
