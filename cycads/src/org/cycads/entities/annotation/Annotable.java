/*
 * Created on 16/09/2009
 */
package org.cycads.entities.annotation;

public interface Annotable<Target, A extends Annotation< ? , Target, ? , ? , ? , M>, M extends AnnotationMethod>
{
	public A createTargetAnnotation(M method, Target target);

	/* Add if Annotation doesn't exist */
	public A addTargetAnnotation(M method, Target target);

}
