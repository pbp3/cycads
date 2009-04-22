/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

public class FixAndFileScoreSystem implements ScoreSystem {

	double			fix;

	FileScoreSystem	fileScoreSystem;

	public FixAndFileScoreSystem(double fix, FileScoreSystem fileScoreSystem) {
		this.fix = fix;
		this.fileScoreSystem = fileScoreSystem;
	}

	@Override
	public double getScore(Double scoreNote) {
		double ret = fix;
		if (scoreNote != null && fileScoreSystem != null) {
			ret = ret * fileScoreSystem.getScore(scoreNote);
		}
		return ret;
	}

}
