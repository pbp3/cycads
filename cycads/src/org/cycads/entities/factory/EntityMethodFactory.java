/*
 * Created on 28/09/2009
 */
package org.cycads.entities.factory;

import org.cycads.entities.annotation.AnnotationMethod;

public interface EntityMethodFactory<M extends AnnotationMethod>
{
	public M getAnnotationMethod(String name);

	//	public M getMethodDefault();

}
