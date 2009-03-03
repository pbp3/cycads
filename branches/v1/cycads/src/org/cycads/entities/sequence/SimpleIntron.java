/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

public class SimpleIntron implements Intron
{
	int	start, end;

	public SimpleIntron(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public int getLength() {
		return getMaxPosition() - getMinPosition() + 1;
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
	public int getMinPosition() {
		return getStart() < getEnd() ? getStart() : getEnd();
	}

	@Override
	public int getMaxPosition() {
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
		if (this.getMinPosition() - o.getMinPosition() != 0) {
			return this.getMinPosition() - o.getMinPosition();
		}
		return this.getMaxPosition() - o.getMaxPosition();
	}

	@Override
	public boolean contains(Intron intron) {
		return this.getMinPosition() <= intron.getMinPosition() && this.getMaxPosition() >= intron.getMaxPosition();
	}

}
