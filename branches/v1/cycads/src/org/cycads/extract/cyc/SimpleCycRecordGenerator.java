package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;

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
import org.cycads.general.ParametersDefault;

public class SimpleCycRecordGenerator implements CycRecordGenerator
{
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

	double				threshold;
	CycIdGenerator		cycIdGenerator;

	public SimpleCycRecordGenerator(double threshold, CycIdGenerator cycIdGenerator) {
		this.threshold = threshold;
		this.cycIdGenerator = cycIdGenerator;
	}

	@Override
	public CycRecord generate(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		String id = getID(annot);
		String prodtype = PFFileConfig.getProductType(annot);
		SimpleCycRecord record = new SimpleCycRecord(prodtype, id);
		record.setStartBase(annot.getSubsequence().getStart());
		record.setEndtBase(annot.getSubsequence().getEnd());
		record.setIntrons((Collection<CycIntron>) annot.getSubsequence().getIntrons());
		record.setName(getDataString(annot, PFFileConfig.getPFFileNamesLocs()));
		record.setComments(getStrings(getDatasAnnotated(annot, PFFileConfig.getPFFileGeneCommentLocs())));
		record.setSynonyms(getStrings(getDatasAnnotated(annot, PFFileConfig.getPFFileGeneSynonymLocs())));
		record.setDBLinks(getDblinks(annot));

		falta EC e function

		// TODO Auto-generated method stub
		return null;
	}

	private Collection<CycDBLink> getDblinks(SubseqAnnotation annot) {
		Collection<CycDBLink> cycDbLinks = new ArrayList<CycDBLink>();
		List<Pattern> patterns = PFFileConfig.getDbLinkDbNamePatterns();
		List<String> values = PFFileConfig.getDbLinkDbNameValues();

		Collection<String> dbLinksStr = getStrings(getDatasAnnotated(annot, PFFileConfig.getPFFileDblinkLocs()));

		for (String dbLinkStr : dbLinksStr) {
			String[] strs = dbLinkStr.split(ParametersDefault.getDbxrefToStringSeparator());
			for (int i = 0; i < patterns.size(); i++) {
				if (patterns.get(i).matcher(strs[0]).matches()) {
					strs[0] = values.get(i);
					break;
				}
			}
			cycDbLinks.add(new SimpleCycDBLink(strs[0], strs[1]));
		}
		return cycDbLinks;
	}

	private String getID(Annotation annot) {
		String id = annot.getNoteValue(ParametersDefault.getPFFileCycIdNoteType());
		if (id == null || id.length() == 0) {
			id = cycIdGenerator.getNewID();
		}
		return id;
	}

	private String getDataString(SubseqAnnotation annot, List<String> locs) {
		String ret;
		ArrayList<Annotation> annotList = new ArrayList<Annotation>();
		for (String loc : locs) {
			ret = getString(getStrings(annot, loc, null, annotList, 0));
			if (ret != null && ret.length() > 0) {
				return ret;
			}
		}
		return null;
	}

	private List<CycAnnotation> getDatasAnnotated(SubseqAnnotation annot, List<String> locs) {
		List<CycAnnotation> ret = null;
		ArrayList<Annotation> annotList = new ArrayList<Annotation>();
		for (String loc : locs) {
			ret = getStrings(annot, loc, ret, annotList, 0);
		}
		return ret;
	}

	private String getString(List<CycAnnotation> cycAnnots) {
		if (cycAnnots != null && !cycAnnots.isEmpty()) {
			return cycAnnots.get(0).getValue();
		}
		return null;
	}

	private Collection<String> getStrings(List<CycAnnotation> cycAnnots) {
		Collection<String> dbLinksStr = new TreeSet<String>();
		for (CycAnnotation cycAnnot : cycAnnots) {
			dbLinksStr.add(cycAnnot.getValue());
		}
		return dbLinksStr;
	}

	private List<CycAnnotation> getStrings(SubseqAnnotation annot, String loc, List<CycAnnotation> ret,
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
			return getStrings(annot.getParents(), loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SYNONYM)) {
			return getStringsBySynonym(annot, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getStringsByNote(annot, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SUBSEQUENCE)) {
			return getStrings(annot.getSubsequence(), loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SEQUENCE)) {
			return getStrings(annot.getSubsequence().getSequence(), loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<CycAnnotation> getStrings(Subsequence subsequence, String loc, List<CycAnnotation> ret,
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
			return getStringsBySynonym(subsequence, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getStringsByNote(subsequence, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_SEQUENCE)) {
			return getStrings(subsequence.getSequence(), loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_DBXREF_ANNOTATION)) {
			return getStringByDbxrefAnnotations(subsequence, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_FUNCTION_ANNOTATION)) {
			return getStringsByFunctions(subsequence.getFunctionAnnotations(null, null), loc, ret, annotList,
				nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<CycAnnotation> getStrings(Sequence sequence, String loc, List<CycAnnotation> ret,
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
			return getStringsBySynonym(sequence, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getStringsByNote(sequence, loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<CycAnnotation> getStringsByFunctions(Collection<SubseqFunctionAnnotation> functionAnnotations,
			String loc, List<CycAnnotation> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
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
				getStringsBySynonym(annot, loc, ret, annotList, nextPosAnnotList + 1);
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
				getStringsByNote(annot, loc, ret, annotList, nextPosAnnotList + 1);
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
				getStrings(annot.getFunction(), loc, ret, annotList, nextPosAnnotList + 1);
			}
		}
		else {
			throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
		}
		return ret;
	}

	private List<CycAnnotation> getStrings(Function function, String loc, List<CycAnnotation> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getStrings(function.getName(), ret, annotList, nextPosAnnotList);
		}
		if (loc.equals("LOC_VALUE")) {
			return getStrings(function.getName(), ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + loc);
	}

	public List<CycAnnotation> getStrings(Collection<SubseqAnnotation> annots, String loc, List<CycAnnotation> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		for (SubseqAnnotation annot : annots) {
			if (annotList.size() > nextPosAnnotList) {
				annotList.set(nextPosAnnotList, annot);
			}
			else {
				annotList.add(annot);
			}
			ret = getStrings(annot, loc, ret, annotList, nextPosAnnotList + 1);
		}
		return ret;
	}

	private List<CycAnnotation> getStringsBySynonym(HasSynonyms annot, String loc, List<CycAnnotation> ret,
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
			ret = getStrings(dbxref, loc, ret, annotList, nextPosAnnotList);
		}
		return ret;
	}

	private List<CycAnnotation> getStrings(Dbxref dbxref, String loc, List<CycAnnotation> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getStrings(dbxref.toString(), ret, annotList, nextPosAnnotList);
		}
		if (loc.equals("LOC_VALUE")) {
			return getStrings(dbxref.getAccession(), ret, annotList, nextPosAnnotList);
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
			return getStringsBySynonym(dbxref, loc, ret, annotList, nextPosAnnotList);
		}
		if (subLoc.equals(LOC_NOTE)) {
			return getStringsByNote(dbxref, loc, ret, annotList, nextPosAnnotList);
		}
		if (subLoc.equals(LOC_DBXREF_ANNOTATION)) {
			return getStringByDbxrefAnnotations(dbxref, loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<CycAnnotation> getStringByDbxrefAnnotations(AnnotationFinder annots, String loc,
			List<CycAnnotation> ret, ArrayList<Annotation> annotList, int nextPosAnnotList) {
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
			ret = getStrings(annot, loc, ret, annotList, nextPosAnnotList + 1);
		}
		return ret;
	}

	private List<CycAnnotation> getStrings(DbxrefAnnotation dbxrefAnnotation, String loc, List<CycAnnotation> ret,
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
			return getStringsBySynonym(dbxrefAnnotation, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getStringsByNote(dbxrefAnnotation, loc, ret, annotList, nextPosAnnotList);
		}
		else if (subLoc.equals(LOC_DBXREF_VALUE)) {
			return getStrings(dbxrefAnnotation.getDbxref(), loc, ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + subLoc + "." + loc);
	}

	private List<CycAnnotation> getStringsByNote(Noteble noteble, String loc, List<CycAnnotation> ret,
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
			ret = getStrings(note, loc, ret, annotList, nextPosAnnotList);
		}
		return ret;
	}

	private List<CycAnnotation> getStrings(Note note, String loc, List<CycAnnotation> ret,
			ArrayList<Annotation> annotList, int nextPosAnnotList) {
		if (loc == null || loc.length() == 0) {
			return getStrings(note.toString(), ret, annotList, nextPosAnnotList);
		}
		if (loc.equals("LOC_VALUE")) {
			return getStrings(note.getValue(), ret, annotList, nextPosAnnotList);
		}
		throw new RuntimeException("Error in the location expression:" + loc);
	}

	private List<CycAnnotation> getStrings(String newStr, List<CycAnnotation> ret, ArrayList<Annotation> annotList,
			int nextPosAnnotList) {
		if (ret == null) {
			ret = new ArrayList<CycAnnotation>();
		}
		ArrayList<Annotation> cycAnnots = new ArrayList<Annotation>(nextPosAnnotList);
		for (int i = 0; i < nextPosAnnotList; i++) {
			cycAnnots.add(annotList.get(i));
		}
		ret.add(new SimpleCycAnnotation(newStr, cycAnnots));
		return ret;
	}

}
