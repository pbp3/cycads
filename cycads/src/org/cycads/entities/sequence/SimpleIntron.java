/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;
import java.util.TreeSet;

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

	public static Collection<Intron> getIntrons(Collection<Intron> introns, int start, int end) {
		Collection<Intron> ret = new TreeSet<Intron>();
		for (Intron intron : introns) {
			Intron newIntron = intron.getIntersection(start, end);
			if (newIntron != null) {
				ret.add(newIntron);
			}
		}
		return ret;
	}

	@Override
	public Intron getIntersection(int start, int end) {
		int min = start > end ? end : start;
		int max = start > end ? start : end;
		if (getEnd() < min || getStart() > max) {
			return null;
		}
		int startIntersection = getStart() > min ? getStart() : min;
		int endIntersection = getEnd() < max ? getEnd() : max;
		return new SimpleIntron(startIntersection, endIntersection);
	}

}
