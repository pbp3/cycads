/*
 * Created on 11/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Noteble;

public interface AnnotationMethod extends Noteble, Comparable<AnnotationMethod> {
	public String getName();

	public double getWeight();

	public void setWeight(double weight);

}