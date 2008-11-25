/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence.feature;

import org.cycads.entities.annotation.ExternalAnnotation;

public interface FeatureToDBAnnotation extends ExternalAnnotation
{

	/**
	 * Getter of the property <tt>sequenceFeature</tt>
	 * 
	 * @return Returns the sequenceFeature.
	 * 
	 */
	public Feature getFeature();

}