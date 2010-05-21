/*
 * Created on 24/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

import org.cycads.entities.annotation.AnnotationMethod;

public interface AnnotationWayListMethods extends List<List<AnnotationMethod>>
{

	public boolean addToAll(AnnotationWayListMethods methodsList);

	public boolean addToAll(List<AnnotationMethod> methods);

	public boolean addToAll(AnnotationMethod method);

}
