/*
 * Created on 17/09/2008
 */
package org.cycads.extract.cyc;

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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CycIntron)) {
			return false;
		}
		else {
			CycIntron o = (CycIntron) obj;
			return this.getStart() == o.getStart() && this.getEnd() == o.getEnd();
		}
	}

}
