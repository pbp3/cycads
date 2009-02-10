/*
 * Created on 24/06/2008
 */
package org.cycads.general.biojava;

import org.biojavax.bio.seq.RichFeature;

public class InvalidFeature extends RuntimeException
{
	RichFeature	feature;

	public InvalidFeature(RichFeature feature) {
		super(feature.toString());
		this.feature = feature;
	}

}
