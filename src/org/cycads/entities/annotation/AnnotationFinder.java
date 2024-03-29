/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface AnnotationFinder<E extends BasicEntity>
{
	/* The arguments nulls are not considerated */
	public <SO extends E, TA extends E> Collection< ? extends Annotation< ? extends SO, ? extends TA>> getAnnotations(
			SO source, TA target, AnnotationMethod method, Collection<Type> types);

	/* The arguments nulls are not considerated */
	public <TA extends E> Collection< ? extends Annotation< ? , ? extends TA>> getAnnotations(Type sourceType,
			TA target, AnnotationMethod method, Collection<Type> types);

	/* The arguments nulls are not considerated */
	public <SO extends E> Collection< ? extends Annotation< ? extends SO, ? >> getAnnotations(SO source,
			Type targetType, AnnotationMethod method, Collection<Type> types);

	/* The arguments nulls are not considerated */
	public Collection< ? extends Annotation< ? , ? >> getAnnotations(Type sourceType, Type targetType,
			AnnotationMethod method, Collection<Type> types);

	public Collection< ? extends Annotation< ? , ? >> getAnnotationsBySynonym(String dbName, String accession);

	public Collection< ? extends Annotation< ? , ? >> getAnnotationsBySynonym(Dbxref synonym);

}
