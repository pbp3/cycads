package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.cycads.entities.BasicEntityAbstract;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;

public class SimpleSubsequence<S extends Sequence< ? , ? >> extends BasicEntityAbstract implements Subsequence<S>
{

	S	seq;
	int	start, end;

	public SimpleSubsequence(S seq, int start, int end, EntityTypeFactory< ? > typeFactory,
			EntityDbxrefFactory< ? > dbxrefFactory, EntityAnnotationFactory annotationFactory) {
		super(typeFactory, dbxrefFactory, annotationFactory);
		this.seq = seq;
		this.start = start;
		this.end = end;
	}

	Collection<Intron>	introns	= new TreeSet<Intron>();

	@Override
	public boolean contains(Subsequence< ? > subseq) {
		if (subseq.getMinPosition() < this.getMinPosition() || subseq.getMaxPosition() > this.getMaxPosition()) {
			return false;
		}
		// introns must be in natural order
		Iterator<Intron> itSubseq = subseq.getIntrons().iterator();
		Intron intronOtherSubseq = null;
		if (itSubseq.hasNext()) {
			intronOtherSubseq = itSubseq.next();
		}
		for (Intron intron : this.getIntrons()) {
			if (intronOtherSubseq == null) {
				return intron.getStart() > subseq.getMaxPosition();
			}
			// get intronOtherSubseq that overlap intron
			while (intronOtherSubseq.getEnd() < intron.getStart()) {
				if (!itSubseq.hasNext()) {
					return intron.getStart() > subseq.getMaxPosition();
				}
				intronOtherSubseq = itSubseq.next();
			}
			if (intronOtherSubseq.getStart() > intron.getStart()) {
				return intron.getStart() > subseq.getMaxPosition();
			}
			else {
				// intronOtherSubseq.min<=intron.min
				while (intronOtherSubseq.getEnd() < intron.getEnd()) {
					// get nextIntron adjacent
					if (itSubseq.hasNext()) {
						Intron nextIntronOtherSubseq = itSubseq.next();
						if (intronOtherSubseq.getEnd() + 1 == nextIntronOtherSubseq.getStart()) {
							intronOtherSubseq = nextIntronOtherSubseq;
						}
						else {
							return intron.getStart() > subseq.getMaxPosition();
						}
					}
					else {
						return intron.getStart() > subseq.getMaxPosition();
					}
				}
			}
		}
		return true;
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
	public Collection<Intron> getIntrons() {
		return introns;
	}

	public Collection<Intron> getIntrons(int start, int end) {
		return SimpleIntron.getIntrons(getIntrons(), start, end);
	}

	@Override
	public int getMaxPosition() {
		return getEnd() >= getStart() ? getEnd() : getStart();
	}

	@Override
	public int getMinPosition() {
		return getEnd() <= getStart() ? getEnd() : getStart();
	}

	public void setMinPosition(int min) {
		if (isPositiveStrand()) {
			this.start = min;
		}
		else {
			this.end = min;
		}
	}

	public void setMaxPosition(int max) {
		if (isPositiveStrand()) {
			this.end = max;
		}
		else {
			this.start = max;
		}
	}

	@Override
	public S getSequence() {
		return seq;
	}

	@Override
	public boolean isPositiveStrand() {
		return getEnd() >= getStart();
	}

	public boolean addIntron(Intron intron) {
		return introns.add(intron);
	}

	public boolean addIntrons(Collection<Intron> introns) {
		return getIntrons().addAll(introns);
	}

	public boolean removeIntron(Intron intron) {
		return introns.remove(intron);
	}

	public void addExon(int start, int end) {
		if (start > end) {
			int aux = start;
			start = end;
			end = aux;
		}

		if (start < getMinPosition()) {
			int minPosOld = getMinPosition();
			setMinPosition(start);
			if (end < minPosOld - 1) {
				addIntron(new SimpleIntron(end + 1, minPosOld - 1));
				return;
			}
		}
		if (end > getMaxPosition()) {
			int maxPosOld = getMaxPosition();
			setMaxPosition(end);
			if (start > maxPosOld + 1) {
				addIntron(new SimpleIntron(maxPosOld + 1, start - 1));
				return;
			}
		}

		Collection<Intron> introns = getIntrons();
		Collection<Intron> intronsToRemove = new ArrayList<Intron>();
		Collection<Intron> intronsToAdd = new ArrayList<Intron>();
		for (Intron intron : introns) {
			if ((intron.getStart() < start && intron.getEnd() > end)
				|| (intron.getStart() >= start && intron.getStart() <= end)
				|| (intron.getEnd() >= start && intron.getEnd() <= end)) {
				intronsToRemove.add(intron);
				if (intron.getStart() < start) {
					intronsToAdd.add(new SimpleIntron(intron.getStart(), start - 1));
				}
				if (intron.getEnd() > end) {
					intronsToAdd.add(new SimpleIntron(end + 1, intron.getEnd()));
				}
			}
		}
		for (Intron intron : intronsToRemove) {
			removeIntron(intron);
		}
		for (Intron intron : intronsToAdd) {
			addIntron(intron);
		}
	}

	@Override
	public Type getEntityType() {
		return typeFactory.getType("SimpleSubsequence");
	}

	@Override
	public String getEntityTypeName() {
		return "SimpleSubsequence";
	}

}
