/*
 * Created on 11/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Noteble;

public interface AnnotationMethod extends Noteble
{
	public static final String	OBJECT_TYPE_NAME	= "Method";

	public String getName();

	public double getWeight();

	public void setWeight(double weight);

}