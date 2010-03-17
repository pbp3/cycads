/*
 * Created on 22/10/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface Annotable
{
	public <TA extends BasicEntity> Annotation< ? , TA> addAnnotation(TA target, AnnotationMethod method,
			String score, Collection<Type> annotationTypes);

	/* The arguments nulls are not considerated */
	public Collection< ? extends Annotation< ? , ? >> getAnnotationsByType(Type targetType, AnnotationMethod method,
			Collection<Type> types);

	//Paremters nulls will be not consider
	public <TA extends BasicEntity> Collection< ? extends Annotation< ? , TA>> getAnnotations(TA target,
			AnnotationMethod method, Collection<Type> annotationTypes);

	public Collection< ? extends Annotation< ? , ? >> getAnnotationsBySynonym(String dbName, String accession);

	public Collection< ? extends Annotation< ? , ? >> getAnnotationsBySynonym(Dbxref synonym);

}
