/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence.feature;

public class SimpleIntron implements Intron
{
	int	start, end;

	public SimpleIntron(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

}
