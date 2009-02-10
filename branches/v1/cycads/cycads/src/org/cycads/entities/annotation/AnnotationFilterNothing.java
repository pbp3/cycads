/*
 * Created on 23/12/2008
 */
package org.cycads.entities.annotation;

public class AnnotationFilterNothing<A extends Annotation< ? , ? , ? >> implements AnnotationFilter<A>
{
	@Override
	public boolean accept(A annotation) {
		return true;
	}

}
