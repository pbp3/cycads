/*
 * Created on 31/10/2008
 */
package org.cycads.entities.annotation;

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
