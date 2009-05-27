package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.general.ParametersDefault;

public class PFFileCycRecordGenerator implements CycRecordGenerator
{

	CycIdGenerator			cycIdGenerator;
	LocInterpreter			locInterpreter;
	double					ecThreshold;
	ScoreSystemCollection	ecScoreSystems;
	double					goThreshold;
	ScoreSystemCollection	goScoreSystems;
	double					koThreshold;
	ScoreSystemCollection	koScoreSystems;

	public PFFileCycRecordGenerator(CycIdGenerator cycIdGenerator, LocInterpreter locInterpreter, double ecThreshold,
			ScoreSystemCollection ecScoreSystems, double goThreshold, ScoreSystemCollection goScoreSystems,
			double koThreshold, ScoreSystemCollection koScoreSystems) {
		this.cycIdGenerator = cycIdGenerator;
		this.locInterpreter = locInterpreter;
		this.ecThreshold = ecThreshold;
		this.ecScoreSystems = goScoreSystems;
		this.goThreshold = goThreshold;
		this.goScoreSystems = ecScoreSystems;
		this.koThreshold = koThreshold;
		this.koScoreSystems = koScoreSystems;
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

		Collection<SimpleCycDbxrefAnnotation> ecs = getCycEcs(annot);
		for (CycDbxrefAnnotation ec : ecs) {
			if (ec.getScore() >= ecThreshold) {
				record.addEC(ec.getAccession());
			}
			Collection<CycDBLink> dbLinks = createDBLink(PFFileConfig.getECDbName(), ec.getAccession());
			for (CycDBLink dbLink : dbLinks) {
				record.addDBLink(dbLink);
			}
			record.addComment(PFFileConfig.getAnnotationComment(ec));
		}

		Collection<SimpleCycDbxrefAnnotation> gos = getCycGos(annot);
		for (CycDbxrefAnnotation go : gos) {
			if (go.getScore() >= goThreshold) {
				record.addGO(go.getAccession());
			}
			Collection<CycDBLink> dbLinks = createDBLink(PFFileConfig.getGODbName(), go.getAccession());
			for (CycDBLink dbLink : dbLinks) {
				record.addDBLink(dbLink);
			}
			record.addComment(PFFileConfig.getAnnotationComment(go));
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

	private Collection<SimpleCycDbxrefAnnotation> getCycEcs(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		return getCycDbxrefAnnots(annot, PFFileConfig.getPFFileECLocs(), PFFileConfig.getECDbName(), ecScoreSystems);
	}

	private Collection<SimpleCycDbxrefAnnotation> getCycGos(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		return getCycDbxrefAnnots(annot, PFFileConfig.getPFFileGOLocs(), PFFileConfig.getGODbName(), goScoreSystems);
	}

	private Collection<SimpleCycDbxrefAnnotation> getCycKos(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		return getCycDbxrefAnnots(annot, PFFileConfig.getPFFileKOLocs(), PFFileConfig.getKODbName(), koScoreSystems);
	}

	private Collection<SimpleCycDbxrefAnnotation> getCycDbxrefAnnots(SubseqAnnotation< ? , ? , ? , ? , ? > annot,
			List<String> locs, String dbName, ScoreSystemCollection scoreSystems) {
		Collection<CycValue> values = locInterpreter.getCycValues(annot, locs);
		Hashtable<String, SimpleCycDbxrefAnnotation> cycAnnots = new Hashtable<String, SimpleCycDbxrefAnnotation>();
		SimpleCycDbxrefAnnotation cycAnnot;
		String annotNumber;
		if (values != null) {
			for (CycValue cycValue : values) {
				annotNumber = cycValue.getValue();
				cycAnnot = cycAnnots.get(annotNumber);
				if (cycAnnot == null) {
					cycAnnot = new SimpleCycDbxrefAnnotation(dbName, annotNumber, cycValue.getAnnotations(),
						scoreSystems);
					cycAnnots.put(annotNumber, cycAnnot);
				}
				else {
					cycAnnot.addAnnotationPath(cycValue.getAnnotations());
				}
			}
		}
		return cycAnnots.values();
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
