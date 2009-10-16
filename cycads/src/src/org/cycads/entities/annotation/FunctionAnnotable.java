/*
 * Created on 16/09/2009
 */
package org.cycads.entities.annotation;

import org.cycads.entities.Function;

public interface FunctionAnnotable<A extends FunctionAnnotation< ? , ? , ? , ? >, F extends Function, M extends AnnotationMethod>
{
	public A createFunctionAnnotation(M method, F function);

	/* Add if Annotation doesn't exist */
	public A addFunctionAnnotation(M method, F function);

}
