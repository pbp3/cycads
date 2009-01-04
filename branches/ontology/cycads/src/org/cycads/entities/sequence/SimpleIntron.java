/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

public class SimpleIntron implements Intron {
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

	public int getMin() {
		return getStart() < getEnd() ? getStart() : getEnd();
	}

	public int getMax() {
		return getStart() > getEnd() ? getStart() : getEnd();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Intron)) {
			return false;
		}
		Intron intron = (Intron) obj;
		return getStart() == intron.getStart() && getEnd() == intron.getEnd();
	}

	public int compareTo(Intron o) {
		if (this.getMin() - o.getMin() != 0) {
			return this.getMin() - o.getMin();
		}
		return this.getMax() - o.getMax();
	}

	public boolean contains(Intron intron) {
		return this.getMin() <= intron.getMin() && this.getMax() >= intron.getMax();
	}

}
