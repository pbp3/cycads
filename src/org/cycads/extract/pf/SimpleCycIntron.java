/*
 * Created on 17/09/2008
 */
package org.cycads.extract.pf;

public class SimpleCycIntron implements CycIntron
{
	int	start, end;

	public SimpleCycIntron(int begin, int end) {
		this.start = begin;
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void shift(int qtty) {
		start += qtty;
		end += qtty;
	}

}
