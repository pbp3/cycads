/*
 * Created on 21/10/2009
 */
package org.cycads.entities.factory;

import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.note.Type;

public interface EntityAnnotationFactory<E extends BasicEntity>
{
	public <SO extends E, TA extends E> Association<SO, TA> createAssociation(SO source, TA target,
			Collection<Type> types);

	//	public <SO extends E, TA extends E> Association<SO, TA> createAssociation(SO source, TA target,
	//			Collection<Type> types);

	public <SO extends E, TA extends E> Annotation<SO, TA> createAnnotation(SO source, TA target,
			Collection<Type> types, AnnotationMethod method, String score);

	//	public <SO extends E, TA extends E> Annotation<SO, TA> createAnnotation(SO source, TA target,
	//			Collection<Type> types, AnnotationMethod method, String score);

}
