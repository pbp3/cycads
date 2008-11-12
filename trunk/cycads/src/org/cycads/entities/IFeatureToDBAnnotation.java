/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

public interface IFeatureToDBAnnotation extends DBAnnotation
{

	/**
	 * Getter of the property <tt>sequenceFeature</tt>
	 * 
	 * @return Returns the sequenceFeature.
	 * 
	 */
	public Feature getFeature();

}