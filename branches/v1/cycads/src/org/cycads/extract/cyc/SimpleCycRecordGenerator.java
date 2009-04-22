package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.general.ParametersDefault;

public class SimpleCycRecordGenerator implements CycRecordGenerator {

	double			threshold;
	CycIdGenerator	cycIdGenerator;
	LocInterpreter	locInterpreter;

	public SimpleCycRecordGenerator(double threshold, CycIdGenerator cycIdGenerator, LocInterpreter locInterpreter) {
		this.threshold = threshold;
		this.cycIdGenerator = cycIdGenerator;
		this.locInterpreter = locInterpreter;
	}

	@Override
	public CycRecord generate(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		String id = getID(annot);
		String prodtype = PFFileConfig.getProductType(annot);
		SimpleCycRecord record = new SimpleCycRecord(prodtype, id);
		record.setStartBase(annot.getSubsequence().getStart());
		record.setEndtBase(annot.getSubsequence().getEnd());
		record.setIntrons(getIntrons(annot));
		record.setName(locInterpreter.getFirstString(annot, PFFileConfig.getPFFileNamesLocs()));
		record.setComments(locInterpreter.getStrings(annot, PFFileConfig.getPFFileGeneCommentLocs()));
		record.setSynonyms(locInterpreter.getStrings(annot, PFFileConfig.getPFFileGeneSynonymLocs()));
		record.setDBLinks(getDblinks(annot));
		Collection<SimpleCycEC> ecs = getCycEcs(annot);
		for (CycEC1 ec : ecs) {
			record.addEC(ec.getEcNumber());
			record.addComment(PFFileConfig.getECScoreComment(ec.getScore()));
			record.addComment(PFFileConfig.getECMethodsComment(ec.getAnnotationsLists()));
		}
		// falta EC e function

		return null;
	}

	private Collection<SimpleCycEC> getCycEcs(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		Collection<CycValue> values = locInterpreter.getCycValues(annot, PFFileConfig.getPFFileECLocs());
	}

	private Collection<CycIntron> getIntrons(SubseqAnnotation annot) {
		Collection<CycIntron> introns = annot.getSubsequence().getIntrons();
		return introns;
	}

	private Collection<CycDBLink> getDblinks(SubseqAnnotation annot) {
		Collection<CycDBLink> cycDbLinks = new ArrayList<CycDBLink>();
		List<Pattern> patterns = PFFileConfig.getDbLinkDbNamePatterns();
		List<String> values = PFFileConfig.getDbLinkDbNameValues();

		Collection<String> dbLinksStr = locInterpreter.getStrings(annot, PFFileConfig.getPFFileDblinkLocs());

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

}
