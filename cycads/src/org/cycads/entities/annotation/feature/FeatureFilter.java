/*
 * Created on 31/10/2008
 */
package org.cycads.entities.annotation.feature;

import org.cycads.entities.annotation.AnnotationFilter;

public interface FeatureFilter extends AnnotationFilter<Feature>
{
	public final static FeatureFilterNothing	FEATURE_FILTER_NOTHING	= new FeatureFilterNothing();

	public static class FeatureFilterNothing implements FeatureFilter
	{
		public boolean accept(Feature feature) {
			return true;
		}
	}
}
