package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.DbxrefDbxrefAnnotation;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;

public class SimpleCycRecordGenerator implements CycRecordGenerator
{
	static final char	LOC_FILTER_REGEX_CHAR	= '#';
	static final char	LOC_FILTER_STRING_CHAR	= '!';
	double				threshold;
	CycIdGenerator		cycIdGenerator;

	public SimpleCycRecordGenerator(double threshold, CycIdGenerator cycIdGenerator) {
		this.threshold = threshold;
		this.cycIdGenerator = cycIdGenerator;
	}

	@Override
	public CycRecord generate(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		String id = getID(annot);
		String type = getType(annot);
		String prodtype = PFFileConfig.getProductType(type);
		SimpleCycRecord record = new SimpleCycRecord(prodtype, id);
		record.setStartBase(annot.getSubsequence().getStart());
		record.setEndtBase(annot.getSubsequence().getEnd());
		record.setIntrons((Collection<CycIntron>) annot.getSubsequence().getIntrons());
		record.setName(getName(annot));
		// TODO Auto-generated method stub
		return null;
	}

	private String getType(Annotation< ? , ? , ? , ? > annot) {
		for (Type type : annot.getTypes()) {
			String typeStr = PFFileConfig.getType(type.getName());
			if (typeStr != null && typeStr.length() != 0) {
				return typeStr;
			}
		}
		return null;
	}

	private String getID(Annotation annot) {
		String id = annot.getNoteValue(ParametersDefault.getPFFileCycIdNoteType());
		if (id == null || id.length() == 0) {
			id = cycIdGenerator.getNewID();
		}
		return id;
	}

	private String getName(SubseqAnnotation annot) {
		List<String> locs = PFFileConfig.getPFFileNamesLocs();
		String ret;
		for (String loc : locs) {
			ret = getString(getStrings(annot, loc));
			if (ret != null && ret.length() > 0) {
				return ret;
			}
		}
	}

	private String getString(List<String> strings) {
		if (strings != null && !strings.isEmpty()) {
			return strings.get(0);
		}
		return null;
	}

	private List<String> getStrings(SubseqAnnotation annot, String loc) {
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
			return getStrings(annot.getParents(), loc);
		}
		else if (subLoc.equals(LOC_SYNONYM)) {
			return getStringsBySynonym(annot, loc);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			return getStringsByNote(annot, loc);
		}
		else if (subLoc.equals(LOC_SUBSEQUENCE)) {
			return getStrings(annot.getSubsequence(), loc);
		}
		else if (subLoc.equals(LOC_SEQUENCE)) {
			return getStrings(annot.getSubsequence().getSequence(), loc);
		}
		return null;
	}

	private List<String> getStringsBySynonym(SubseqAnnotation annot, String loc, List<String> ret) {
		String subLoc = "*";
		boolean isRegexFilter = loc.startsWith(LOC_FILTER_REGEX_CHAR + "");
		boolean isStringFilter = loc.startsWith(LOC_FILTER_STRING_CHAR + "");
		int i;
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
			for (Dbxref dbxref : dbxrefs) {
				if (!dbxref.getAccession().matches(subLoc)) {
					dbxrefs.remove(dbxref);
				}
			}
		}
		else {
			dbxrefs = annot.getSynonyms(subLoc);
		}

		for (Dbxref dbxref : dbxrefs) {
			getStrings(dbxref, loc, ret);
		}
		return ret;
	}

	private List<String> getStrings(Dbxref dbxref, String loc, List<String> ret) {
		if (loc == null || loc.length() == 0) {
			if (ret == null) {
				ret = new ArrayList<String>();
			}
			ret.add(dbxref.toString());
		}
		if (loc.equals("LOC_VALUE")) {
			return dbxref.getAccession();
		}
		else {
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
			if (subLoc.equals(LOC_DBXREF)) {
				return getStringByDbxrefAnnotations(dbxref, loc);
			}
			else if (subLoc.equals(LOC_SYNONYM)) {

			}
		}
		return null;
	}

	private String getStringByDbxrefAnnotations(Dbxref dbxref, String loc) {
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
		Collection<DbxrefDbxrefAnnotation> dbxrefAnnots;
		???? continua daqui
		if (subLoc.equals("*")) {
			dbxrefs = annot.getSynonyms();
		}
		else {
			dbxrefs = annot.getSynonyms(subLoc);
		}
		String ret;
		for (Dbxref dbxref : dbxrefs) {
			ret = getString(dbxref, loc);
			if (ret != null && ret.length() > 0) {
				return ret;
			}
		}
		return ret;
	}

	public String getString(Collection<SubseqAnnotation> annots, String loc) {
		String ret;
		for (SubseqAnnotation parent : annots) {
			ret = getStrings(parent, loc);
			if (ret != null && ret.length() > 0) {
				return ret;
			}
		}
		return null;
	}

}
