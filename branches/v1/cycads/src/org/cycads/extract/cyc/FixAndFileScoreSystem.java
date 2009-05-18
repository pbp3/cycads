/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

public class FixAndFileScoreSystem implements FixScoreSystem
{

	public double	fix;

	FileScoreSystem	fileScoreSystem;

	public FixAndFileScoreSystem(double fix, FileScoreSystem fileScoreSystem) {
		this.fix = fix;
		this.fileScoreSystem = fileScoreSystem;
	}

	@Override
	public double getScore(Double scoreNote) {
		double ret = fix;
		if (scoreNote != null) {
			if (fileScoreSystem != null) {
				ret = ret * fileScoreSystem.getScore(scoreNote);
			}
			else {
				ret = ret * scoreNote;
			}
		}
		return ret;
	}

	@Override
	public double getFixValue() {
		return fix;
	}

}
