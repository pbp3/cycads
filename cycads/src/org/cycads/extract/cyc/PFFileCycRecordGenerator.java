package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.Feature;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.general.ParametersDefault;

public class PFFileCycRecordGenerator implements CycRecordGenerator
{

	CycIdGenerator			cycIdGenerator;
	LocInterpreter			locInterpreter;
	double					ecThreshold;
	ScoreSystemCollection	ecScoreSystems;
	double					goThreshold;
	ScoreSystemCollection	goScoreSystems;
	private Type			cycIdNoteType	= null;

	//	double					koThreshold;
	//	ScoreSystemCollection	koScoreSystems;

	public PFFileCycRecordGenerator(CycIdGenerator cycIdGenerator, LocInterpreter locInterpreter, double ecThreshold,
			ScoreSystemCollection ecScoreSystems, double goThreshold, ScoreSystemCollection goScoreSystems) {
		this.cycIdGenerator = cycIdGenerator;
		this.locInterpreter = locInterpreter;
		this.ecThreshold = ecThreshold;
		this.ecScoreSystems = ecScoreSystems;
		this.goThreshold = goThreshold;
		this.goScoreSystems = goScoreSystems;
		//		this.koThreshold = koThreshold;
		//		this.koScoreSystems = koScoreSystems;
	}

	@Override
	public SimpleCycRecord generate(Annotation< ? extends Subsequence, ? extends Feature> annot) {
		String id = getID(annot);
		String prodtype = PFFileConfig.getProductType(annot.getTarget().getName());
		SimpleCycRecord record = new SimpleCycRecord(prodtype, id);
		try {
			Subsequence subseq = annot.getSource();
			record.setStartBase(subseq.getStart());
			record.setEndBase(subseq.getEnd());
			record.setIntrons(getIntrons(subseq));
			String name = locInterpreter.getFirstString(annot, PFFileConfig.getPFFileNamesLocs());
			record.setName(name);
			record.setFunctions(getFunctions(annot, record));
			record.setComments(locInterpreter.getStrings(annot, PFFileConfig.getPFFileGeneCommentLocs()));
			Collection<String> syns = locInterpreter.getStrings(annot, PFFileConfig.getPFFileGeneSynonymLocs());
			if (record.getName() != null && record.getName().length() > 0) {
				syns.remove(record.getName());
			}
			record.setSynonyms(syns);
			record.setDBLinks(getDblinks(annot));

			Collection<CycDbxrefAnnotationPaths> ecs = locInterpreter.getCycDbxrefPathAnnots(annot,
				PFFileConfig.getPFFileECLocs(), ecScoreSystems);
			for (CycDbxrefAnnotationPaths ec : ecs) {
				if (ec.getScore() >= ecThreshold) {
					record.addEC(ec.getAccession());
				}
				Collection<CycDBLink> dbLinks = createDBLink(ec.getDbName(), ec.getAccession());
				for (CycDBLink dbLink : dbLinks) {
					record.addDBLink(dbLink);
				}
				record.addComment(PFFileConfig.getAnnotationComment(ec));
			}

			Collection<CycDbxrefAnnotationPaths> gos = locInterpreter.getCycDbxrefPathAnnots(annot,
				PFFileConfig.getPFFileGOLocs(), goScoreSystems);
			for (CycDbxrefAnnotationPaths go : gos) {
				if (go.getScore() >= goThreshold) {
					record.addGO(go.getAccession());
				}
				else {
					Collection<CycDBLink> dbLinks = createDBLink(go.getDbName(), go.getAccession());
					for (CycDBLink dbLink : dbLinks) {
						record.addDBLink(dbLink);
					}
				}
				record.addComment(PFFileConfig.getAnnotationComment(go));
			}

			Collection<StringAndPath> cycValueRets = locInterpreter.getCycValues(annot,
				PFFileConfig.getPFFileGeneCommentAnnotationsLocs());
			for (StringAndPath cycValueRet : cycValueRets) {
				if (cycValueRet instanceof CycDbxrefAnnotationPathsRet) {
					CycDbxrefAnnotationPaths cycDbxrefAnnotationPaths = ((CycDbxrefAnnotationPathsRet) cycValueRet).getCycDbxrefAnnotationPaths();
					List<Annotation> initialAnnots = cycValueRet.getAnnotations();
					for (List<Annotation> annots : cycDbxrefAnnotationPaths.getAnnotationPaths()) {
						annots.addAll(0, initialAnnots);
					}
					record.addComment(PFFileConfig.getAnnotationComment(cycDbxrefAnnotationPaths));
				}
				else {
					throw new RuntimeException("Error in the gene comment annotation loc.");
				}
			}

		}
		catch (RecordErrorException e) {
			System.err.println(record.getId() + " - " + e.getMessage());
			return null;
		}
		return record;
	}

	private Collection<CycFunction> getFunctions(Annotation< ? extends Subsequence, ? > annot, CycRecord record)
			throws RecordErrorException {
		String functionName = locInterpreter.getFirstString(annot, PFFileConfig.getPFFileFunctionsLocs());
		if (functionName == null || functionName.length() == 0) {
			functionName = record.getName();
			if (functionName == null || functionName.length() == 0) {
				throw new RecordErrorException("Function:" + functionName + ";");
			}
		}
		else {
			String name = record.getName();
			if (name == null || name.length() == 0) {
				record.setName(functionName);
			}
		}
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

	private Collection<CycIntron> getIntrons(Subsequence subseq) {
		Collection<CycIntron> introns = subseq.getIntrons();
		return introns;
	}

	private Collection<CycDBLink> getDblinks(Annotation< ? extends Subsequence, ? > annot) {
		Collection<CycDBLink> cycDbLinks = new ArrayList<CycDBLink>();

		Collection<String> dbLinksStr = locInterpreter.getStrings(annot, PFFileConfig.getPFFileDblinkLocs());

		for (String dbLinkStr : dbLinksStr) {
			String[] strs = dbLinkStr.split(ParametersDefault.getDbxrefToStringSeparator());
			if (strs.length == 2) {
				cycDbLinks.addAll(createDBLink(strs[0], strs[1]));
			}
			else {
				System.err.println("DBLink error:" + dbLinkStr);
			}
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

	private String getID(Annotation< ? , ? > annot) {
		if (cycIdNoteType == null) {
			cycIdNoteType = annot.getNoteType(ParametersDefault.getPFFileCycIdNoteType());
		}
		String id = annot.getNoteValue(cycIdNoteType);
		if (id == null) {
			id = cycIdGenerator.getNewID();
			annot.addNote(cycIdNoteType, id);
		}
		else if (id.length() == 0) {
			id = cycIdGenerator.getNewID();
			annot.setNoteValue(cycIdNoteType, id);
		}
		return id;
	}

}
