/*
 * Created on 24/03/2010
 */
package org.cycads.general;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


public class TransformStringToDouble
{
	public double getScoreDouble(String score) {
		if (score == null) {
			return ParametersDefault.getAnnotationScoreDefault();
		}
		else {
			NumberFormat numberFormat;
			if (score.indexOf('%') != -1) {
				numberFormat = NumberFormat.getPercentInstance(Locale.US);
			}
			else if (score.indexOf('E') != -1) {
				numberFormat = NumberFormat.getNumberInstance(Locale.US);
			}
			else {
				numberFormat = NumberFormat.getNumberInstance(Locale.US);
			}
			try {
				return numberFormat.parse(score).doubleValue();
			}
			catch (ParseException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}
}
