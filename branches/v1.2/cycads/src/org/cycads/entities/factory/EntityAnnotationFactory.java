/*
 * Created on 21/10/2009
 */
package org.cycads.entities.factory;

import org.cycads.entities.EntityObject;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.note.Type;

public interface EntityAnnotationFactory<E extends EntityObject>
{
	public <SO extends E, TA extends E> Association<SO, TA> createAssociation(SO source, TA target, Type... type);

	//	public <SO extends E, TA extends E> Association<SO, TA> createAssociation(SO source, TA target,
	//			Collection<Type> types);

	public <SO extends E, TA extends E> Annotation<SO, TA> createAnnotation(SO source, TA target,
			AnnotationMethod method, String score, Type... type);

	//	public <SO extends E, TA extends E> Annotation<SO, TA> createAnnotation(SO source, TA target,
	//			Collection<Type> types, AnnotationMethod method, String score);

}
