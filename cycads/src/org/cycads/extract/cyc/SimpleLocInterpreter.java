/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import org.cycads.entities.EntityObject;
import org.cycads.entities.Function;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public class SimpleLocInterpreter implements LocInterpreter
{

	static final char		LOC_FILTER_REGEX_CHAR	= '#';
	static final char		LOC_FILTER_STRING_CHAR	= '!';

	static final String		LOC_SYNONYM				= "SY";
	static final String		LOC_PARENT				= "P";
	static final String		LOC_NOTE				= "N";
	static final String		LOC_SUBSEQUENCE			= "SS";
	static final String		LOC_SEQUENCE			= "SQ";
	static final String		LOC_DBXREF_ANNOTATION	= "XA";
	static final String		LOC_FUNCTION_ANNOTATION	= "FA";
	static final String		LOC_FUNCTION			= "F";
	static final String		LOC_VALUE				= "V";
	static final String		LOC_DBXREF_VALUE		= "XV";
	static final String		LOC_DBXREF_SET			= "XR";

	ScoreSystemsContainer	scoreSystemsContainer;
	LocContainer			locContainer;

	public SimpleLocInterpreter(ScoreSystemsContainer scoreSystemsContainer, LocContainer locContainer) {
		this.scoreSystemsContainer = scoreSystemsContainer;
		this.locContainer = locContainer;
	}

	public String getFirstString(Annotation< ? extends Subsequence, ? > annot, List<String> locs) {
		String ret;
		List<String> values = getStrings(annot, locs);
		for (String value : values) {
			if (value != null && value.length() > 0) {
				return value;
			}
		}
		return null;
	}

	public List<String> getStrings(Annotation< ? extends Subsequence, ? > annot, List<String> locs) {
		return getStrings(getCycValues(annot, locs));
	}

	private List<String> getStrings(List<StringAndPath> stringAndPaths) {
		List<String> ret = new ArrayList<String>();
		for (StringAndPath stringAndPath : stringAndPaths) {
			String value = stringAndPath.getValue();
			if (!ret.contains(value)) {
				ret.add(stringAndPath.getValue());
			}
		}
		return ret;
	}

	@Override
	public List<StringAndPath> getCycValues(Annotation< ? extends Subsequence, ? > annot, List<String> locs) {
		List<StringAndPath> ret = new ArrayList<StringAndPath>();
		ArrayList<Annotation> annotList = new ArrayList<Annotation>();
		for (String loc : locs) {
			ret = getCycValues(annot, loc, ret, annotList, 0);
		}
		return ret;
	}

	private List<StringAndPath> getCycValues(Annotation< ? extends Subsequence, ? > annot, String loc,
			List<StringAndPath> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return ret;
		}
		int i = loc.indexOf('.');
		String subLoc;
		if (i == -1) {
			subLoc = loc;
			loc = "";
		}
		else {
			subLoc = loc.substring(0, i);
			loc = loc.substring(i + 1);
		}

		if (subLoc.equals(LOC_PARENT)) {
			return getValueAndPaths(annot.getParents(), loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SYNONYM)) {
			return getCycValuesBySynonym(annot, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getCycValuesByNote(annot, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SUBSEQUENCE)) {
			return getCycValuesSubsequence(annot.getSource(), loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SEQUENCE)) {
			return getCycValuesSequence(annot.getSource().getSequence(), loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_DBXREF_SET)) {
			return getCycValuesByDbxrefSet(annot, loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<StringAndPath> getCycValuesBySynonym(HasSynonyms annot, String loc, List<StringAndPath> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		String subLoc = "*";
		boolean isRegexFilter = loc.startsWith(LOC_FILTER_REGEX_CHAR + "");
		boolean isStringFilter = loc.startsWith(LOC_FILTER_STRING_CHAR + "");
		int i = 0;
		if (isRegexFilter) {
			i = loc.indexOf(LOC_FILTER_REGEX_CHAR, 1);
		}
		else if (isStringFilter) {
			i = loc.indexOf(LOC_FILTER_STRING_CHAR, 1);
		}
		if (isRegexFilter || isStringFilter) {
			subLoc = loc.substring(1, i);
			if (i + 1 < loc.length()) {
				loc = loc.substring(i + 2);
			}
			else {
				loc = "";
			}
		}
		Collection< ? extends Dbxref> dbxrefs;
		if (subLoc.equals("*")) {
			dbxrefs = annot.getSynonyms();
		}
		else if (isRegexFilter) {
			dbxrefs = annot.getSynonyms();
			Collection<Dbxref> dbxrefs1 = new ArrayList<Dbxref>(dbxrefs);
			for (Dbxref dbxref : dbxrefs1) {
				if (!dbxref.getDbName().matches(subLoc)) {
					dbxrefs.remove(dbxref);
				}
			}
		}
		else {
			dbxrefs = annot.getSynonyms(subLoc);
		}

		for (Dbxref dbxref : dbxrefs) {
			ret = getCycValues(dbxref, loc, ret, annotList, nextPosAnnotList);
		}
		return ret;
	}

	private List<StringAndPath> getCycValuesByDbxrefSet(Annotation< ? extends Subsequence, ? > annot, String loc,
			List<StringAndPath> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (!loc.startsWith(LOC_FILTER_STRING_CHAR + "")) {
			throw new RuntimeException("Error in the location expression:" + loc);
		}
		int i = loc.indexOf(LOC_FILTER_STRING_CHAR, 1);
		if (i == -1) {
			throw new RuntimeException("Error in the location expression:" + loc);
		}
		String subLoc;
		subLoc = loc.substring(1, i);
		if (i + 1 < loc.length()) {
			loc = loc.substring(i + 2);
		}
		else {
			loc = "";
		}
		double threshold = Double.NEGATIVE_INFINITY;
		boolean hasThreshold = loc.startsWith(LOC_FILTER_STRING_CHAR + "");
		if (hasThreshold) {
			i = loc.indexOf(LOC_FILTER_STRING_CHAR, 1);
			if (i == -1) {
				throw new RuntimeException("Error in the location expression:" + loc);
			}
			threshold = Double.parseDouble(loc.substring(1, i));
			if (i + 1 < loc.length()) {
				loc = loc.substring(i + 2);
			}
			else {
				loc = "";
			}
		}
		Collection<CycDbxrefAnnotationPaths> dbxrefAnnotationsPaths = getCycDbxrefPathAnnots(annot,
			locContainer.getLocs(subLoc), scoreSystemsContainer.getScoreSystems(subLoc));
		for (CycDbxrefAnnotationPaths dbxrefAnnotationPaths : dbxrefAnnotationsPaths) {
			if (!hasThreshold || dbxrefAnnotationPaths.getScore() >= threshold) {
				ret = getCycValues(dbxrefAnnotationPaths, loc, ret, annotList, nextPosAnnotList);
			}
		}
		return ret;
	}

	private List<StringAndPath> getCycValues(CycDbxrefAnnotationPaths dbxrefAnnotationPaths, String loc,
			List<StringAndPath> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getCycDbxrefAnnotation(dbxrefAnnotationPaths, ret, annotList, nextPosAnnotList);
		}
		List<List<Annotation>> paths = dbxrefAnnotationPaths.getAnnotationPaths();
		for (List<Annotation> annots : paths) {
			int i = nextPosAnnotList;
			for (Annotation annot : annots) {
				if (annotList.size() > i) {
					annotList.set(i, annot);
				}
				else {
					annotList.add(annot);
				}
				i++;
			}
			ret = getCycValues(dbxrefAnnotationPaths.getDbxref(), loc, ret, annotList, i);
		}
		return ret;
	}

	private List<StringAndPath> getCycDbxrefAnnotation(CycDbxrefAnnotationPaths dbxrefAnnotationPaths,
			List<StringAndPath> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (ret == null) {
			ret = new ArrayList<StringAndPath>();
		}
		ArrayList<Annotation> cycAnnots = new ArrayList<Annotation>(nextPosAnnotList);
		for (int i = 0; i < nextPosAnnotList; i++) {
			cycAnnots.add(annotList.get(i));
		}
		ret.add(new SimpleCycDbxrefAnnotationPathsRet(dbxrefAnnotationPaths, cycAnnots));
		return ret;
	}

	private List<StringAndPath> getCycValuesSubsequence(Subsequence subsequence, String loc, List<StringAndPath> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		int i = loc.indexOf('.');
		String subLoc;
		if (i == -1) {
			subLoc = loc;
			loc = "";
		}
		else {
			subLoc = loc.substring(0, i);
			loc = loc.substring(i + 1);
		}

		if (subLoc.equals(LOC_SYNONYM)) {
			return getCycValuesBySynonym(subsequence, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getCycValuesByNote(subsequence, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SEQUENCE)) {
			return getCycValuesSequence(subsequence.getSequence(), loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_DBXREF_ANNOTATION)) {
			return getCycValuesByDbxrefAnnotations(subsequence, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_FUNCTION_ANNOTATION)) {
			return getCycValuesByFunctions(subsequence.getFunctionAnnotations(null, null), loc, ret, annotList,
				nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<StringAndPath> getCycValuesSequence(Sequence sequence, String loc, List<StringAndPath> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		int i = loc.indexOf('.');
		String subLoc;
		if (i == -1) {
			subLoc = loc;
			loc = "";
		}
		else {
			subLoc = loc.substring(0, i);
			loc = loc.substring(i + 1);
		}

		if (subLoc.equals(LOC_SYNONYM)) {
			return getCycValuesBySynonym(sequence, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getCycValuesByNote(sequence, loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<StringAndPath> getCycValuesByFunctions(
			Collection<Annotation< ? extends Subsequence, ? extends Function>> functionAnnotations, String loc,
			List<StringAndPath> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
		int i = loc.indexOf('.');
		String subLoc;
		if (i == -1) {
			subLoc = loc;
			loc = "";
		}
		else {
			subLoc = loc.substring(0, i);
			loc = loc.substring(i + 1);
		}

		if (subLoc.equals(LOC_SYNONYM)) {
			for (Annotation< ? extends Subsequence, ? extends Function> annot : functionAnnotations) {
				if (annotList.size() > nextPosAnnotList) {
					annotList.set(nextPosAnnotList, annot);
				}
				else {
					annotList.add(annot);
				}
				getCycValuesBySynonym(annot, loc, ret, annotList, nextPosAnnotList + 1);
			}
		}
		else if (subLoc.equals(LOC_NOTE)) {
			for (Annotation< ? extends Subsequence, ? extends Function> annot : functionAnnotations) {
				if (annotList.size() > nextPosAnnotList) {
					annotList.set(nextPosAnnotList, annot);
				}
				else {
					annotList.add(annot);
				}
				getCycValuesByNote(annot, loc, ret, annotList, nextPosAnnotList + 1);
			}
		}
		else if (subLoc.equals(LOC_FUNCTION)) {
			for (Annotation< ? extends Subsequence, ? extends Function> annot : functionAnnotations) {
				if (annotList.size() > nextPosAnnotList) {
					annotList.set(nextPosAnnotList, annot);
				}
				else {
					annotList.add(annot);
				}
				getCycValuesFunction(annot.getTarget(), loc, ret, annotList, nextPosAnnotList + 1);
			}
		}
		else {
			throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
		}
		return ret;
	}

	private List<StringAndPath> getCycValuesFunction(Function function, String loc, List<StringAndPath> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getCycValues(function.getName(), ret, annotList, nextPosAnnotList);
		}
		if (loc.equals(LOC_VALUE)) {
			return getCycValues(function.getName(), ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + loc);
	}

	private List<StringAndPath> getCycValues(Collection<Annotation< ? extends Subsequence, ? >> annots, String loc,
			List<StringAndPath> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
		for (Annotation< ? extends Subsequence, ? > annot : annots) {
			if (annotList.size() > nextPosAnnotList) {
				annotList.set(nextPosAnnotList, annot);
			}
			else {
				annotList.add(annot);
			}
			ret = getCycValues(annot, loc, ret, annotList, nextPosAnnotList + 1);
		}
		return ret;
	}

	private List<StringAndPath> getCycValues(Dbxref dbxref, String loc, List<StringAndPath> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getCycDbxref(dbxref, ret, annotList, nextPosAnnotList);
		}
		if (loc.equals(LOC_VALUE)) {
			return getCycValues(dbxref.getAccession(), ret, annotList, nextPosAnnotList);
		}
		int i = loc.indexOf('.');
		String subLoc;
		if (i == -1) {
			subLoc = loc;
			loc = "";
		}
		else {
			subLoc = loc.substring(0, i);
			loc = loc.substring(i + 1);
		}
		if (subLoc.equals(LOC_SYNONYM)) {
			return getCycValuesBySynonym(dbxref, loc, ret, annotList, nextPosAnnotList);
		}
		if (subLoc.equals(LOC_NOTE)) {
			return getCycValuesByNote(dbxref, loc, ret, annotList, nextPosAnnotList);
		}
		if (subLoc.equals(LOC_DBXREF_ANNOTATION)) {
			return getCycValuesByDbxrefAnnotations(dbxref, loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<StringAndPath> getCycValuesByDbxrefAnnotations(EntityObject entityObject, String loc,
			List<StringAndPath> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
		String subLoc = "*";
		boolean isRegexFilter = loc.startsWith(LOC_FILTER_REGEX_CHAR + "");
		boolean isStringFilter = loc.startsWith(LOC_FILTER_STRING_CHAR + "");
		int i = 0;
		if (isRegexFilter) {
			i = loc.indexOf(LOC_FILTER_REGEX_CHAR, 1);
		}
		else if (isStringFilter) {
			i = loc.indexOf(LOC_FILTER_STRING_CHAR, 1);
		}
		if (isRegexFilter || isStringFilter) {
			subLoc = loc.substring(1, i);
			if (i + 1 < loc.length()) {
				loc = loc.substring(i + 2);
			}
			else {
				loc = "";
			}
		}
		Collection<Annotation< ? , ? extends Dbxref>> dbxrefAnnots;
		if (subLoc.equals("*")) {
			dbxrefAnnots = entityObject.getAnnotations(null, null);
		}
		else if (isRegexFilter) {
			dbxrefAnnots = entityObject.getAnnotations(null, null);
			Collection<Annotation< ? , ? extends Dbxref>> dbxrefAnnots1 = new ArrayList<Annotation< ? , ? extends Dbxref>>(
				dbxrefAnnots);
			for (Annotation< ? , ? extends Dbxref> dbxrefAnnotation : dbxrefAnnots1) {
				if (!dbxrefAnnotation.getTarget().getDbName().matches(subLoc)) {
					dbxrefAnnots.remove(dbxrefAnnotation);
				}
			}
		}
		else {
			dbxrefAnnots = entityObject.getAnnotations(subLoc);
		}

		for (Annotation< ? , ? extends Dbxref> annot : dbxrefAnnots) {
			if (annotList.size() > nextPosAnnotList) {
				annotList.set(nextPosAnnotList, annot);
			}
			else {
				annotList.add(annot);
			}
			ret = getCycValuesDbxrefAnnotation(annot, loc, ret, annotList, nextPosAnnotList + 1);
		}
		return ret;
	}

	private List<StringAndPath> getCycValuesDbxrefAnnotation(Annotation< ? , ? extends Dbxref> dbxrefAnnotation,
			String loc, List<StringAndPath> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
		int i = loc.indexOf('.');
		String subLoc;
		if (i == -1) {
			subLoc = loc;
			loc = "";
		}
		else {
			subLoc = loc.substring(0, i);
			loc = loc.substring(i + 1);
		}
		if (subLoc.equals(LOC_SYNONYM)) {
			return getCycValuesBySynonym(dbxrefAnnotation, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getCycValuesByNote(dbxrefAnnotation, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_DBXREF_VALUE)) {
			return getCycValues(dbxrefAnnotation.getTarget(), loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<StringAndPath> getCycValuesByNote(Noteble noteble, String loc, List<StringAndPath> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		String subLoc = "*";
		boolean isRegexFilter = loc.startsWith(LOC_FILTER_REGEX_CHAR + "");
		boolean isStringFilter = loc.startsWith(LOC_FILTER_STRING_CHAR + "");
		int i = 0;
		if (isRegexFilter) {
			i = loc.indexOf(LOC_FILTER_REGEX_CHAR, 1);
		}
		else if (isStringFilter) {
			i = loc.indexOf(LOC_FILTER_STRING_CHAR, 1);
		}
		if (isRegexFilter || isStringFilter) {
			subLoc = loc.substring(1, i);
			if (i + 1 < loc.length()) {
				loc = loc.substring(i + 2);
			}
			else {
				loc = "";
			}
		}
		Collection<Note> notes;
		if (subLoc.equals("*")) {
			notes = noteble.getNotes();
		}
		else if (isRegexFilter) {
			notes = noteble.getNotes();
			Collection<Note> notes1 = new ArrayList<Note>(notes);
			for (Note note : notes1) {
				if (!note.getType().getName().matches(subLoc)) {
					notes.remove(note);
				}
			}
		}
		else {
			notes = noteble.getNotes(subLoc);
		}

		for (Note note : notes) {
			ret = getCycValues(note, loc, ret, annotList, nextPosAnnotList);
		}
		return ret;
	}

	private List<StringAndPath> getCycValues(Note note, String loc, List<StringAndPath> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getCycValues(note.toString(), ret, annotList, nextPosAnnotList);
		}
		if (loc.equals(LOC_VALUE)) {
			return getCycValues(note.getValue(), ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + loc);
	}

	private List<StringAndPath> getCycValues(String newStr, List<StringAndPath> ret, ArrayList<Annotation> annotList,
			int nextPosAnnotList) {
		if (ret == null) {
			ret = new ArrayList<StringAndPath>();
		}
		ArrayList<Annotation> cycAnnots = new ArrayList<Annotation>(nextPosAnnotList);
		for (int i = 0; i < nextPosAnnotList; i++) {
			cycAnnots.add(annotList.get(i));
		}
		ret.add(new SimpleCycValueRet(newStr, cycAnnots));
		return ret;
	}

	private List<StringAndPath> getCycDbxref(Dbxref dbxref, List<StringAndPath> ret, ArrayList<Annotation> annotList,
			int nextPosAnnotList) {
		if (ret == null) {
			ret = new ArrayList<StringAndPath>();
		}
		ArrayList<Annotation> cycAnnots = new ArrayList<Annotation>(nextPosAnnotList);
		for (int i = 0; i < nextPosAnnotList; i++) {
			cycAnnots.add(annotList.get(i));
		}
		ret.add(new SimpleCycDbxrefRet(dbxref, cycAnnots));
		return ret;
	}

	@Override
	public Collection<CycDbxrefAnnotationPaths> getCycDbxrefPathAnnots(Annotation< ? extends Subsequence, ? > annot,
			List<String> locs, ScoreSystemCollection scoreSystems) {
		Collection<StringAndPath> values = getValueAndPaths(annot, locs);
		Hashtable<String, CycDbxrefAnnotationPaths> cycAnnots = new Hashtable<String, CycDbxrefAnnotationPaths>();
		CycDbxrefAnnotationPaths cycAnnot;
		Dbxref dbxref;
		if (values != null) {
			for (StringAndPath cycValue : values) {
				if (cycValue instanceof CycDbxrefRet) {
					dbxref = ((CycDbxrefRet) cycValue).getDbxref();
					cycAnnot = cycAnnots.get(dbxref.toString());
					if (cycAnnot == null) {
						cycAnnot = new SimpleCycDbxrefAnnotationPaths(dbxref, cycValue.getAnnotations(), scoreSystems);
						cycAnnots.put(dbxref.toString(), cycAnnot);
					}
					else {
						cycAnnot.addAnnotationPath(cycValue.getAnnotations());
					}
				}
				else {
					throw new RuntimeException("Invalid dbxref locs:" + locs.toString());
				}
			}
		}
		return cycAnnots.values();
	}

}
