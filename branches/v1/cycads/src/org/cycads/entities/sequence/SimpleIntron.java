/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

public class SimpleIntron implements Intron
{
	int	start, end;

	public SimpleIntron(int start, int end) {
		if (start > end) {
			int aux = start;
			start = end;
			end = aux;
		}
		this.start = start;
		this.end = end;
	}

	@Override
	public int getLength() {
		return getEnd() - getStart() + 1;
	}

	@Override
	public int getStart() {
		return start;
	}

	@Override
	public int getEnd() {
		return end;
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
		if (this.getStart() - o.getStart() != 0) {
			return this.getStart() - o.getStart();
		}
		return this.getEnd() - o.getEnd();
	}

	@Override
	public boolean contains(Intron intron) {
		return this.getStart() <= intron.getStart() && this.getEnd() >= intron.getEnd();
	}

}
