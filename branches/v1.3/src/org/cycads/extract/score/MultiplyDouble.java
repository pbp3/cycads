/*
 * Created on 24/03/2010
 */
package org.cycads.extract.score;

public class MultiplyDouble implements TransformDouble
{
	double	factor;

	public MultiplyDouble(double factor) {
		this.factor = factor;
	}

	@Override
	public double transform(double score) {
		return score * getFactor();
	}

	public double getFactor() {
		return factor;
	}

}
