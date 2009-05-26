package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.synonym.EC;
import org.cycads.general.ParametersDefault;

public class PFFileCycRecordGenerator implements CycRecordGenerator
{

	double					threshold;
	CycIdGenerator			cycIdGenerator;
	LocInterpreter			locInterpreter;
	ScoreSystemCollection	ecScoreSystems;

	public PFFileCycRecordGenerator(double threshold, CycIdGenerator cycIdGenerator, LocInterpreter locInterpreter,
			ScoreSystemCollection ecScoreSystems) {
		this.threshold = threshold;
		this.cycIdGenerator = cycIdGenerator;
		this.locInterpreter = locInterpreter;
		this.ecScoreSystems = ecScoreSystems;
	}

	@Override
	public CycRecord generate(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		String id = getID(annot);
		String prodtype = PFFileConfig.getProductType(annot);
		SimpleCycRecord record = new SimpleCycRecord(prodtype, id);
		record.setStartBase(annot.getSubsequence().getStart());
		record.setEndBase(annot.getSubsequence().getEnd());
		record.setIntrons(getIntrons(annot));
		record.setName(locInterpreter.getFirstString(annot, PFFileConfig.getPFFileNamesLocs()));
		record.setComments(locInterpreter.getStrings(annot, PFFileConfig.getPFFileGeneCommentLocs()));
		Collection<String> syns = locInterpreter.getStrings(annot, PFFileConfig.getPFFileGeneSynonymLocs());
		if (record.getName() != null && record.getName().length() > 0) {
			syns.remove(record.getName());
		}
		record.setSynonyms(syns);
		record.setDBLinks(getDblinks(annot));
		Collection<SimpleCycEC> ecs = getCycEcs(annot);
		for (CycEC ec : ecs) {
			if (ec.getScore() >= threshold) {
				record.addEC(ec.getEcNumber());
			}
			Collection<CycDBLink> dbLinks = createDBLink(EC.DBNAME, ec.getEcNumber());
			for (CycDBLink dbLink : dbLinks) {
				record.addDBLink(dbLink);
			}
			record.addComment(PFFileConfig.getECComment(ec));
		}
		record.setFunctions(getFunctions(annot));
		return record;
	}

	private Collection<CycFunction> getFunctions(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		String functionName = locInterpreter.getFirstString(annot, PFFileConfig.getPFFileFunctionsLocs());
		CycFunction cycFunction = new SimpleCycFunction(functionName);
		Collection<String> syns = locInterpreter.getStrings(annot, PFFileConfig.getPFFileFunctionSynonymLocs());
		if (functionName != null && functionName.length() > 0) {
			syns.remove(functionName);
		}
		cycFunction.setSynonyms(syns);
		cycFunction.setComments(locInterpreter.getStrings(annot, PFFileConfig.getPFFileFunctionCommentLocs()));
		ArrayList<CycFunction> ret = new ArrayList<CycFunction>(1);
		ret.add(cycFunction);
		return ret;
	}

	private Collection<SimpleCycEC> getCycEcs(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		Collection<CycValue> values = locInterpreter.getCycValues(annot, PFFileConfig.getPFFileECLocs());
		Hashtable<String, SimpleCycEC> cycEcs = new Hashtable<String, SimpleCycEC>();
		SimpleCycEC cycEC;
		String ecNumber;
		if (values != null) {
			for (CycValue cycValue : values) {
				ecNumber = cycValue.getValue();
				cycEC = cycEcs.get(ecNumber);
				if (cycEC == null) {
					cycEC = new SimpleCycEC(ecNumber, cycValue.getAnnotations(), ecScoreSystems);
					cycEcs.put(ecNumber, cycEC);
				}
				else {
					cycEC.addAnnotationPath(cycValue.getAnnotations());
				}
			}
		}
		return cycEcs.values();
		//		List<SimpleCycEC> ret = new ArrayList<SimpleCycEC>();
		//		for (SimpleCycEC cycEc : cycEcs.values()) {
		//			if (cycEc.getScore() >= threshold) {
		//				ret.add(cycEc);
		//			}
		//		}
		//		return ret;
	}

	private Collection<CycIntron> getIntrons(SubseqAnnotation annot) {
		Collection<CycIntron> introns = annot.getSubsequence().getIntrons();
		return introns;
	}

	private Collection<CycDBLink> getDblinks(SubseqAnnotation annot) {
		Collection<CycDBLink> cycDbLinks = new ArrayList<CycDBLink>();

		Collection<String> dbLinksStr = locInterpreter.getStrings(annot, PFFileConfig.getPFFileDblinkLocs());

		for (String dbLinkStr : dbLinksStr) {
			String[] strs = dbLinkStr.split(ParametersDefault.getDbxrefToStringSeparator());
			cycDbLinks.addAll(createDBLink(strs[0], strs[1]));
		}
		return cycDbLinks;
	}

	private Collection<CycDBLink> createDBLink(String dbName, String accession) {
		Collection<CycDBLink> cycDbLinks = new ArrayList<CycDBLink>();
		dbName = dbName.trim();
		accession = accession.trim();
		List<Pattern> changePatterns = PFFileConfig.getDbLinkDbNameChangePatterns();
		List<String> changeValues = PFFileConfig.getDbLinkDbNameChangeValues();
		List<Pattern> copyPatterns = PFFileConfig.getDbLinkDbNameCopyPatterns();
		List<String> copyValues = PFFileConfig.getDbLinkDbNameCopyValues();
		List<Pattern> patternsRemove = PFFileConfig.getDbLinkDbNameRemovePatterns();
		if (!PFFileConfig.matches(dbName, patternsRemove)) {
			boolean foundDbName = false;
			for (int i = 0; i < changePatterns.size(); i++) {
				if (changePatterns.get(i).matcher(dbName).matches()) {
					foundDbName = cycDbLinks.add(new SimpleCycDBLink(changeValues.get(i), accession));
				}
			}
			if (!foundDbName) {
				cycDbLinks.add(new SimpleCycDBLink(dbName, accession));
			}
			for (int i = 0; i < copyPatterns.size(); i++) {
				if (copyPatterns.get(i).matcher(dbName).matches()) {
					cycDbLinks.add(new SimpleCycDBLink(copyValues.get(i), accession));
				}
			}
		}
		return cycDbLinks;
	}

	private String getID(Annotation annot) {
		String id = annot.getNoteValue(ParametersDefault.getPFFileCycIdNoteType());
		if (id == null || id.length() == 0) {
			id = cycIdGenerator.getNewID();
			annot.addNote(ParametersDefault.getPFFileCycIdNoteType(), id);
		}
		return id;
	}

}
