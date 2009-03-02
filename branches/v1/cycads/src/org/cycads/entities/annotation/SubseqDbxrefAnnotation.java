/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface SubseqDbxrefAnnotation<A extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? >, T extends Type, M extends AnnotationMethod>
		extends SubseqAnnotation<A, X, T, M>
{

}
