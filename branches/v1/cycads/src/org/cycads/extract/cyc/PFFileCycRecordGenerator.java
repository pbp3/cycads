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

	double					threshold;
	CycIdGenerator			cycIdGenerator;
	LocInterpreter			locInterpreter;
	ScoreSystemCollection	scoreSystems;

	public PFFileCycRecordGenerator(double threshold, CycIdGenerator cycIdGenerator, LocInterpreter locInterpreter,
			ScoreSystemCollection scoreSystems) {
		this.threshold = threshold;
		this.cycIdGenerator = cycIdGenerator;
		this.locInterpreter = locInterpreter;
		this.scoreSystems = scoreSystems;
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
			record.addEC(ec.getEcNumber());
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
					cycEC = new SimpleCycEC(ecNumber, cycValue.getAnnotations(), scoreSystems);
					cycEcs.put(ecNumber, cycEC);
				}
				else {
					cycEC.addAnnotationPath(cycValue.getAnnotations());
				}
			}
		}
		List<SimpleCycEC> ret = new ArrayList<SimpleCycEC>();
		for (SimpleCycEC cycEc : cycEcs.values()) {
			if (cycEc.getScore() >= threshold) {
				ret.add(cycEc);
			}
		}
		return ret;
	}

	private Collection<CycIntron> getIntrons(SubseqAnnotation annot) {
		Collection<CycIntron> introns = annot.getSubsequence().getIntrons();
		return introns;
	}

	private Collection<CycDBLink> getDblinks(SubseqAnnotation annot) {
		Collection<CycDBLink> cycDbLinks = new ArrayList<CycDBLink>();
		List<Pattern> patterns = PFFileConfig.getDbLinkDbNamePatterns();
		List<String> values = PFFileConfig.getDbLinkDbNameValues();
		List<Pattern> patternsRemove = PFFileConfig.getDbLinkDbNameRemovePatterns();

		Collection<String> dbLinksStr = locInterpreter.getStrings(annot, PFFileConfig.getPFFileDblinkLocs());

		for (String dbLinkStr : dbLinksStr) {
			String[] strs = dbLinkStr.split(ParametersDefault.getDbxrefToStringSeparator());
			if (!PFFileConfig.matches(strs[0], patternsRemove)) {
				boolean foundDbName = false;
				for (int i = 0; i < patterns.size(); i++) {
					if (patterns.get(i).matcher(strs[0]).matches()) {
						foundDbName = cycDbLinks.add(new SimpleCycDBLink(values.get(i), strs[1]));
					}
				}
				if (!foundDbName) {
					cycDbLinks.add(new SimpleCycDBLink(strs[0], strs[1]));
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
