/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface DBAnnotationMethod extends AnnotationMethod<DBAnnotationMethod>
{

	public Collection<DBAnnotation> getDBAnnotations();

}