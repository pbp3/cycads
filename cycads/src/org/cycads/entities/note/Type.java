/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note;

import org.cycads.entities.annotation.AnnotationObjectType;

public interface Type extends Comparable<Type>, AnnotationObjectType
{
	public String getName();

	public String getDescription();

}
