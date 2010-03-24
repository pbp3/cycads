/*
 * Created on 24/03/2010
 */
package org.cycads.extract.score;

public class MultiplyDouble implements TransformDouble
{
	int	factor;

	public MultiplyDouble(int factor) {
		this.factor = factor;
	}

	@Override
	public double transform(double score) {
		return score * getFactor();
	}

	public int getFactor() {
		return factor;
	}

}
