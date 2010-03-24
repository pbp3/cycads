/*
 * Created on 24/03/2010
 */
package org.cycads.general;

import org.cycads.entities.annotation.Annotation;

public interface AnnotationFilter
{
	public boolean isValid(Annotation annot);
}
