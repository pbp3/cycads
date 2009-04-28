/*
 * Created on 14/04/2009
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.DbxrefAnnotation;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.annotation.SubseqFunctionAnnotation;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.Function;
import org.cycads.entities.synonym.HasSynonyms;

public class SimpleLocInterpreter implements LocInterpreter {

	static final char	LOC_FILTER_REGEX_CHAR	= '#';
	static final char	LOC_FILTER_STRING_CHAR	= '!';

	static final String	LOC_SYNONYM				= "SY";
	static final String	LOC_PARENT				= "P";
	static final String	LOC_NOTE				= "N";
	static final String	LOC_SUBSEQUENCE			= "SS";
	static final String	LOC_SEQUENCE			= "SQ";
	static final String	LOC_DBXREF_ANNOTATION	= "XA";
	static final String	LOC_FUNCTION_ANNOTATION	= "FA";
	static final String	LOC_FUNCTION			= "F";
	static final String	LOC_VALUE				= "V";
	static final String	LOC_DBXREF_VALUE		= "XV";

	public String getFirstString(SubseqAnnotation annot, List<String> locs) {
		String ret;
		List<CycValue> cycAnnots;
		ArrayList<Annotation> annotList = new ArrayList<Annotation>();
		for (String loc : locs) {
			cycAnnots = getCycValues(annot, loc, null, annotList, 0);
			if (cycAnnots != null && !cycAnnots.isEmpty()) {
				ret = cycAnnots.get(0).getValue();
				if (ret != null && ret.length() > 0) {
					return ret;
				}
			}
		}
		return null;
	}

	public Collection<String> getStrings(SubseqAnnotation annot, List<String> locs) {
		return getStrings(getCycValues(annot, locs));
	}

	private Collection<String> getStrings(List<CycValue> cycAnnots) {
		Collection<String> dbLinksStr = new TreeSet<String>();
		for (CycValue cycAnnot : cycAnnots) {
			dbLinksStr.add(cycAnnot.getValue());
		}
		return dbLinksStr;
	}

	public List<CycValue> getCycValues(SubseqAnnotation annot, List<String> locs) {
		List<CycValue> ret = new ArrayList<CycValue>();
		ArrayList<Annotation> annotList = new ArrayList<Annotation>();
		for (String loc : locs) {
			ret = getCycValues(annot, loc, ret, annotList, 0);
		}
		return ret;
	}

	private List<CycValue> getCycValues(SubseqAnnotation annot, String loc, List<CycValue> ret,
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

		if (subLoc.equals(LOC_PARENT)) {
			return getCycValues(annot.getParents(), loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SYNONYM)) {
			return getCycValuesBySynonym(annot, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getCycValuesByNote(annot, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SUBSEQUENCE)) {
			return getCycValues(annot.getSubsequence(), loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SEQUENCE)) {
			return getCycValues(annot.getSubsequence().getSequence(), loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<CycValue> getCycValues(Subsequence subsequence, String loc, List<CycValue> ret,
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
			return getCycValues(subsequence.getSequence(), loc, ret, annotList, nextPosAnnotList);
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

	private List<CycValue> getCycValues(Sequence sequence, String loc, List<CycValue> ret,
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

	private List<CycValue> getCycValuesByFunctions(Collection<SubseqFunctionAnnotation> functionAnnotations,
			String loc, List<CycValue> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
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
			for (SubseqFunctionAnnotation annot : functionAnnotations) {
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
			for (SubseqFunctionAnnotation annot : functionAnnotations) {
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
			for (SubseqFunctionAnnotation annot : functionAnnotations) {
				if (annotList.size() > nextPosAnnotList) {
					annotList.set(nextPosAnnotList, annot);
				}
				else {
					annotList.add(annot);
				}
				getCycValues(annot.getFunction(), loc, ret, annotList, nextPosAnnotList + 1);
			}
		}
		else {
			throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
		}
		return ret;
	}

	private List<CycValue> getCycValues(Function function, String loc, List<CycValue> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getCycValues(function.getName(), ret, annotList, nextPosAnnotList);
		}
		if (loc.equals(LOC_VALUE)) {
			return getCycValues(function.getName(), ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + loc);
	}

	private List<CycValue> getCycValues(Collection<SubseqAnnotation> annots, String loc, List<CycValue> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		for (SubseqAnnotation annot : annots) {
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

	private List<CycValue> getCycValuesBySynonym(HasSynonyms annot, String loc, List<CycValue> ret,
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
		Collection<Dbxref> dbxrefs;
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

	private List<CycValue> getCycValues(Dbxref dbxref, String loc, List<CycValue> ret, ArrayList<Annotation> annotList,
			int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getCycValues(dbxref.toString(), ret, annotList, nextPosAnnotList);
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

	private List<CycValue> getCycValuesByDbxrefAnnotations(AnnotationFinder annots, String loc, List<CycValue> ret,
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
		Collection<DbxrefAnnotation> dbxrefAnnots;
		if (subLoc.equals("*")) {
			dbxrefAnnots = annots.getDbxrefAnnotations(null, null);
		}
		else if (isRegexFilter) {
			dbxrefAnnots = annots.getDbxrefAnnotations(null, null);
			Collection<DbxrefAnnotation> dbxrefAnnots1 = new ArrayList<DbxrefAnnotation>(dbxrefAnnots);
			for (DbxrefAnnotation dbxrefAnnotation : dbxrefAnnots1) {
				if (!dbxrefAnnotation.getDbxref().getDbName().matches(subLoc)) {
					dbxrefAnnots.remove(dbxrefAnnotation);
				}
			}
		}
		else {
			dbxrefAnnots = annots.getDbxrefAnnotations(subLoc);
		}

		for (DbxrefAnnotation annot : dbxrefAnnots) {
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

	private List<CycValue> getCycValues(DbxrefAnnotation dbxrefAnnotation, String loc, List<CycValue> ret,
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
			return getCycValuesBySynonym(dbxrefAnnotation, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getCycValuesByNote(dbxrefAnnotation, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_DBXREF_VALUE)) {
			return getCycValues(dbxrefAnnotation.getDbxref(), loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<CycValue> getCycValuesByNote(Noteble noteble, String loc, List<CycValue> ret,
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

	private List<CycValue> getCycValues(Note note, String loc, List<CycValue> ret, ArrayList<Annotation> annotList,
			int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getCycValues(note.toString(), ret, annotList, nextPosAnnotList);
		}
		if (loc.equals(LOC_VALUE)) {
			return getCycValues(note.getValue(), ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + loc);
	}

	private List<CycValue> getCycValues(String newStr, List<CycValue> ret, ArrayList<Annotation> annotList,
			int nextPosAnnotList) {
		if (ret == null) {
			ret = new ArrayList<CycValue>();
		}
		ArrayList<Annotation> cycAnnots = new ArrayList<Annotation>(nextPosAnnotList);
		for (int i = 0; i < nextPosAnnotList; i++) {
			cycAnnots.add(annotList.get(i));
		}
		ret.add(new SimpleCycValue(newStr, cycAnnots));
		return ret;
	}

}
