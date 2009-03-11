/*
 * Created on 02/03/2009
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface DbxrefDbxrefAnnotation<X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Annotation<X, T, M>
{
	public X getDbxrefSource();

	public X getDbxrefTarget();
}