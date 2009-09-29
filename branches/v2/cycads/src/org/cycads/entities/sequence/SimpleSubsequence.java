package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.cycads.entities.Function;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.SimpleSubseqAnnotation;
import org.cycads.entities.annotation.SimpleSubseqDbxrefAnnotation;
import org.cycads.entities.annotation.SimpleSubseqFunctionAnnotation;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.annotation.SubseqDbxrefAnnotation;
import org.cycads.entities.annotation.SubseqFunctionAnnotation;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.SimpleHasSynonymsNoteble;

public class SimpleSubsequence<S extends Sequence< ? , ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends SimpleHasSynonymsNoteble<X>
		implements Subsequence<S, SimpleSubseqAnnotation< ? , ? , ? , ? , ? >, Function, X, T, M>
{

	protected EntityFactory< ? extends X, ? extends M, ? extends T, ? , ? >	factory;

	S																		seq;
	int																		start, end;

	public SimpleSubsequence(EntityFactory< ? extends X, ? extends M, ? extends T, ? , ? > factory, S seq, int start,
			int end) {
		super(factory);
		this.seq = seq;
		this.start = start;
		this.end = end;
		this.factory = factory;
	}

	Collection<Intron>												introns			= new TreeSet<Intron>();
	ArrayList<SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >>	dbxrefAnnots	= new ArrayList<SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >>();
	ArrayList<SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? >>	functionAnnots	= new ArrayList<SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? >>();
	ArrayList<SimpleSubseqAnnotation< ? , ? , ? , ? , ? >>			allAnnots		= new ArrayList<SimpleSubseqAnnotation< ? , ? , ? , ? , ? >>();

	@Override
	public boolean contains(Subsequence< ? , ? , ? , ? , ? , ? > subseq) {
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

	@Override
	public int getMaxPosition() {
		return getEnd() >= getStart() ? getEnd() : getStart();
	}

	@Override
	public int getMinPosition() {
		return getEnd() <= getStart() ? getEnd() : getStart();
	}

	@Override
	public S getSequence() {
		return seq;
	}

	@Override
	public boolean isPositiveStrand() {
		return getEnd() >= getStart();
	}

	@Override
	public SubseqDbxrefAnnotation< ? , ? , ? , ? , ? > addDbxrefTargetAnnotation(M method, X dbxref) {
		SubseqDbxrefAnnotation< ? , ? , ? , ? , ? > annot;
		Collection< ? extends SubseqDbxrefAnnotation< ? , ? , ? , ? , ? >> annots = getDbxrefAnnotations(method, dbxref);
		if (annots.isEmpty()) {
			annot = createDbxrefTargetAnnotation(method, dbxref);
		}
		else {
			annot = annots.iterator().next();
		}
		return annot;
	}

	@Override
	public SubseqFunctionAnnotation< ? , ? , ? , ? , ? > addFunctionAnnotation(M method, Function function) {
		SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? > annot;
		Collection< ? extends SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? >> annots = getFunctionAnnotations(
			method, function);
		if (annots.isEmpty()) {
			annot = createFunctionAnnotation(method, function);
		}
		else {
			annot = annots.iterator().next();
		}
		functionAnnots.add(annot);
		return annot;
	}

	@Override
	public SubseqAnnotation< ? , ? , ? , ? , ? > addAnnotation(T type, M method) {
		Collection<T> types = new ArrayList<T>();
		types.add(type);
		SubseqAnnotation< ? , ? , ? , ? , ? > annot;
		Collection< ? extends SubseqAnnotation< ? , ? , ? , ? , ? >> annots = getAnnotations(method, types, null);
		if (annots.isEmpty()) {
			annot = createAnnotation(type, method);
		}
		else {
			annot = annots.iterator().next();
		}
		return annot;
	}

	@Override
	public SubseqAnnotation< ? , ? , ? , ? , ? > createAnnotation(T type, M method) {
		SimpleSubseqAnnotation< ? , ? , ? , ? , ? > annot = new SimpleSubseqAnnotation<Annotation< ? , ? , ? , ? >, SimpleSubsequence<S, X, T, M>, X, T, M>(
			factory, this, method);
		allAnnots.add(annot);
		return annot;
	}

	@Override
	public SubseqDbxrefAnnotation< ? , ? , ? , ? , ? > createDbxrefTargetAnnotation(M method, X dbxref) {
		SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? > annot = new SimpleSubseqDbxrefAnnotation<Annotation< ? , ? , ? , ? >, SimpleSubsequence<S, X, T, M>, X, T, M>(
			factory, this, dbxref, method);
		dbxrefAnnots.add(annot);
		allAnnots.add(annot);
		return annot;
	}

	@Override
	public SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? > createFunctionAnnotation(M method, Function function) {
		SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? > annot = new SimpleSubseqFunctionAnnotation<Annotation< ? , ? , ? , ? >, SimpleSubsequence<S, X, T, M>, X, T, M>(
			factory, function, this, method);
		functionAnnots.add(annot);
		allAnnots.add(annot);
		return annot;
	}

	@Override
	public Collection< ? extends SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? >> getFunctionAnnotations(M method,
			Function function) {
		Collection<SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? >> ret = new ArrayList<SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? >>();
		for (SimpleSubseqFunctionAnnotation< ? , ? , ? , ? , ? > annot : functionAnnots) {
			if ((function == null || function.equals(annot.getFunction()))
				&& (method == null || method.equals(annot.getAnnotationMethod()))) {
				ret.add(annot);
			}
		}
		return ret;
	}

	@Override
	public Collection< ? extends SimpleSubseqAnnotation< ? , ? , ? , ? , ? >> getAnnotations(M method,
			Collection<T> types, X synonym) {
		Collection<SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >> ret = new ArrayList<SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >>();
		for (SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? > annot : dbxrefAnnots) {
			if ((synonym == null || annot.isSynonym(synonym.getDbName(), synonym.getAccession()))
				&& (method == null || method.equals(annot.getAnnotationMethod()))) {
				boolean isType = true;
				if (types != null && !types.isEmpty()) {
					for (T type : types) {
						isType = isType && annot.hasType(type.getName());
					}
				}
				if (isType) {
					ret.add(annot);
				}
			}
		}
		return ret;
	}

	@Override
	public Collection< ? extends SimpleSubseqAnnotation< ? , ? , ? , ? , ? >> getAnnotations(
			AnnotationFilter<SimpleSubseqAnnotation< ? , ? , ? , ? , ? >> filter) {
		Collection<SimpleSubseqAnnotation< ? , ? , ? , ? , ? >> ret = new ArrayList<SimpleSubseqAnnotation< ? , ? , ? , ? , ? >>();
		for (SimpleSubseqAnnotation< ? , ? , ? , ? , ? > annot : allAnnots) {
			if (filter.accept(annot)) {
				ret.add(annot);
			}
		}
		return ret;
	}

	@Override
	public Collection< ? extends SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >> getDbxrefAnnotations(M method,
			X dbxref) {
		Collection<SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >> ret = new ArrayList<SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >>();
		for (SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? > annot : dbxrefAnnots) {
			if ((dbxref == null || dbxref.equals(annot.getDbxref()))
				&& (method == null || method.equals(annot.getAnnotationMethod()))) {
				ret.add(annot);
			}
		}
		return ret;
	}

	@Override
	public Collection< ? extends SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >> getDbxrefAnnotations(
			String dbxrefDbname) {
		Collection<SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >> ret = new ArrayList<SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? >>();
		for (SimpleSubseqDbxrefAnnotation< ? , ? , ? , ? , ? > annot : dbxrefAnnots) {
			if ((dbxrefDbname == null || dbxrefDbname.equals(annot.getDbxref().getDbName()))) {
				ret.add(annot);
			}
		}
		return ret;
	}

	public boolean addIntron(Intron intron) {
		return introns.add(intron);
	}

	public boolean removeIntron(Intron intron) {
		return introns.remove(intron);
	}

	public boolean addExon(int start, int end) {
		if (start > end) {
			int aux = start;
			start = end;
			end = aux;
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
		return true;
	}

}
