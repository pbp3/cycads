/*
 * Created on 28/09/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface DBxrefTargetAnnotationFinder<A extends Annotation< ? , Target, ? , X, T, M>, Target extends Dbxref< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends AnnotationFinder<A, Target, X, T, M>
{
	public Collection< ? extends A> getDbxrefAnnotations(String dbxrefDbname);

}
