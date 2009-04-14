package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class FileScoreSystem implements ScoreSystem {
	ArrayList<Score>	scores;

	public FileScoreSystem(String fileName) {
		ResourceBundle fileMap = ResourceBundle.getBundle(fileName);
		Enumeration<String> keys = fileMap.getKeys();
		scores = new ArrayList<Score>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			scores.add(new Score(Double.parseDouble(key), Double.parseDouble(fileMap.getString(key))));
		}
		Collections.sort(scores);
	}

	@Override
	public double getScore(Double scoreNote) {
		int i = Collections.binarySearch(scores, new Score(scoreNote, Double.NEGATIVE_INFINITY));
		if (i >= 0) {
			return Double.NEGATIVE_INFINITY;
		}
		i = -i - 1;
		if (i == 0) {
			return 0;
		}
		i--;
		return scores.get(i).value;
	}

	final class Score implements Comparable<Score> {
		double	limit;
		double	value;

		public Score(double limit, double value) {
			this.limit = limit;
			this.value = value;
		}

		@Override
		public int compareTo(Score arg) {
			if (limit < arg.limit) {
				return -1;
			}
			else if (limit > arg.limit) {
				return 1;
			}
			else if (value < arg.value) {
				return -1;
			}
			else if (value > arg.value) {
				return 1;
			}
			return 0;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Score)) {
				return false;
			}
			Score o = (Score) obj;
			return (limit == o.limit) && (value == o.value);
		}

	}

}
