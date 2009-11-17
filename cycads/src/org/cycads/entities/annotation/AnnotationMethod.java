/*
 * Created on 11/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.BasicEntity;

public interface AnnotationMethod extends BasicEntity
{
	public static final String	ENTITY_TYPE_NAME	= "AnnotationMethod";

	public String getName();

	public double getWeight();

	public void setWeight(double weight);

}