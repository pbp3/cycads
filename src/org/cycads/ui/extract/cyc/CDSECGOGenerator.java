/*
 /*
 * Created on 05/01/2009
 * Modified on 15/03/2010
 */
package org.cycads.ui.extract.cyc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cycads.entities.Feature;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.extract.cyc.CycIdGenerator;
import org.cycads.extract.cyc.CycRecord;
import org.cycads.extract.cyc.CycRecordGenerator;
import org.cycads.extract.cyc.CycStream;
import org.cycads.extract.cyc.LocContainer;
import org.cycads.extract.cyc.OrganismCycIdGenerator;
import org.cycads.extract.cyc.PFFileConfig;
import org.cycads.extract.cyc.PFFileCycRecordGenerator;
import org.cycads.extract.cyc.PFFileStream;
import org.cycads.extract.cyc.SimpleLocInterpreter;
import org.cycads.extract.score.AnnotationScoreSystem;
import org.cycads.extract.score.ScoreSystemsContainer;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class CDSECGOGenerator
{

	public static void main(String[] args) {
		EntityFactory factory = EntityFactory.factoryDefault;
		File file = Tools.getFileToSave(args, 0, Config.annotationGeneratorFileName(), Messages.pfGeneratorChooseFile());
		if (file == null) {
			return;
		}
		Organism organism = Tools.getOrganism(args, 1, Config.annotationGeneratorOrganismNumber(),
			Messages.pfGeneratorChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}
		String seqSynonym = Tools.getString(args, 2, Config.annotationGeneratorSeqSynonym(),
			Messages.pfGeneratorChooseSeqSynonym());
		String seqDbname = null, seqAccession = null;
		while (seqSynonym != null && !seqSynonym.equals("*") && seqDbname == null && seqAccession == null) {
			String[] strs = seqSynonym.split(":");
			if (strs.length == 2) {
				seqDbname = strs[0];
				seqAccession = strs[1];
			}
			else {
				if (args.length > 2) {
					throw new RuntimeException("Sequence synonym error: " + seqSynonym);
				}
				else {
					seqSynonym = Tools.getString(args, 2, seqSynonym, Messages.pfGeneratorChooseSeqSynonym());
				}
			}
		}
		if (seqSynonym == null) {
			return;
		}

		String seqVersion = Tools.getString(args, 3, Config.annotationGeneratorSeqVersion(),
			Messages.pfGeneratorChooseSeqVersion());
		if (seqVersion == null) {
			return;
		}

		Double ecThreshold = Tools.getDouble(args, 4, Config.annotationGeneratorEcThreshold(), Messages.pfGeneratorChooseEcThreshold());
		if (ecThreshold == null) {
			return;
		}

		Double goThreshold = Tools.getDouble(args, 5, Config.annotationGeneratorGoThreshold(), Messages.pfGeneratorChooseGoThreshold());
		if (goThreshold == null) {
			return;
		}

		// add by PBP, for further export file format option
		int fileFormat = Tools.getInteger(args, 6, Config.annotationGeneratorOutFormat(),
			Messages.CDSECGOGeneratorChooseFileFormat());

		Progress progress = new ProgressPrintInterval(System.out, Messages.pfGeneratorStepShowInterval());
		try {
			progress.init(Messages.pfGeneratorInitMsg(file.getPath()));
			Collection<Sequence< ? , ? >> seqs;
			if (seqDbname == null) {
				seqs = organism.getSequences(seqVersion);
			}
			else {
				seqs = organism.getSequences(factory.getDbxref(seqDbname, seqAccession));
			}

			List<String> typesStr = PFFileConfig.getTypes();
			Collection<Feature> features = new ArrayList<Feature>(typesStr.size());
			for (String typeStr : typesStr) {
				features.add(factory.getFeature(typeStr));
			}

			CycStream outStream;
			switch (fileFormat){
				case 1:
					{
						boolean sequenceLocation = Config.SequenceLocation();
						outStream = new PFFileStream(file, Config.pfGeneratorFileHeader(), sequenceLocation);
						break;
					}
				case 2:
					{
						outStream = new AnnotationByLineFileStream(file, Config.annotationGeneratorFileHeader());
						break;
					}
				case 3:
					{
						outStream = new RecordByLineFileStream(file, Config.annotationGeneratorFileHeader());
						break;
					}
				default:
					{

					}

			}
			CycIdGenerator cycIdGenerator = new OrganismCycIdGenerator(organism);

			LocAndScores locAndScores = new LocAndScores();
			AnnotationScoreSystem ecScoreSystemCollection = locAndScores.getScoreSystems("ec");
			AnnotationScoreSystem goScoreSystemCollection = locAndScores.getScoreSystems("go");

			CycRecordGenerator cycRecordGenerator = new PFFileCycRecordGenerator(cycIdGenerator,
				new SimpleLocInterpreter(locAndScores, locAndScores), ecThreshold, ecScoreSystemCollection,
				goThreshold, goScoreSystemCollection);
			for (Feature feature : features) {
				for (Sequence seq : seqs) {
					Collection<Subsequence> subseqs = seq.getSubsequences();
					for (Subsequence subseq : subseqs) {
						Collection<Annotation<Subsequence, Feature>> cdss = (Collection<Annotation<Subsequence, Feature>>) subseq.getAnnotations(
							feature, null, null);
						for (Annotation<Subsequence, Feature> cds : cdss) {
							CycRecord record = cycRecordGenerator.generate(cds);
							if (record != null) {
								outStream.print(record);
								progress.completeStep();
							}
						}
					}
				}
			}
			progress.finish(Messages.pfGeneratorFinalMsg(progress.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static class LocAndScores implements ScoreSystemsContainer, LocContainer
	{
		@Override
		public AnnotationScoreSystem getScoreSystems(String scoreName) {
			return PFFileConfig.getScoreSystems(scoreName);
		}

		@Override
		public List<String> getLocs(String locName) {
			return PFFileConfig.getLocs(locName);
		}

	}

}
