/*
 * Created on 12/11/2008
 */
package org.cycads.loaders.gff3;

public class Exon implements Comparable<Exon>
{
	int	start, end;

	public Exon(int start, int end) {
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
		if (obj == null || !(obj instanceof Exon)) {
			return false;
		}
		Exon exon = (Exon) obj;
		return getStart() == exon.getStart() && getEnd() == exon.getEnd();
	}

	public int compareTo(Exon o) {
		if (this.getMin() - o.getMin() != 0) {
			return this.getMin() - o.getMin();
		}
		return this.getMax() - o.getMax();
	}

}
