/*
 * Created on 24/11/2008
 */
package org.cycads.entities.biojava;

import org.cycads.generators.MethodFilter;

public class ThresholdMethodFilter implements MethodFilter
{
	float	threshold;

	public ThresholdMethodFilter(float threshold) {
		this.threshold = threshold;
	}

	public boolean isValid(Method method) {
		return method.getWeight() >= threshold;
	}

}
