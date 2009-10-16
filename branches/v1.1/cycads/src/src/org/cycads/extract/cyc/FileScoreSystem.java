package org.cycads.extract.cyc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;

public class FileScoreSystem implements ScoreSystem
{
	ArrayList<Score>	scores;

	public FileScoreSystem(String fileName) throws FileNotFoundException, IOException {
		Properties fileMap = new Properties();
		fileMap.load(new FileInputStream(fileName));
		Enumeration<Object> keys = fileMap.keys();
		scores = new ArrayList<Score>();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			scores.add(new Score(Double.parseDouble(key), Double.parseDouble(fileMap.getProperty(key))));
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

	final class Score implements Comparable<Score>
	{
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
