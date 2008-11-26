/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence.feature;

import org.cycads.entities.annotation.DBLink;

public interface FeatureToDBAnnotation extends DBLink
{

	/**
	 * Getter of the property <tt>sequenceFeature</tt>
	 * 
	 * @return Returns the sequenceFeature.
	 * 
	 */
	public Feature getFeature();

}