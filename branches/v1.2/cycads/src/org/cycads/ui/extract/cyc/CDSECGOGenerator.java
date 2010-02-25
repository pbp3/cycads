/*
 * Created on 05/01/2009
 */
package org.cycads.ui.extract.cyc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
import org.cycads.extract.cyc.LocContainer;
import org.cycads.extract.cyc.OrganismCycIdGenerator;
import org.cycads.extract.cyc.PFFileConfig;
import org.cycads.extract.cyc.PFFileCycRecordGenerator;
import org.cycads.extract.cyc.ScoreSystemCollection;
import org.cycads.extract.cyc.ScoreSystemsContainer;
import org.cycads.extract.cyc.SimpleLocInterpreter;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class CDSECGOGenerator
{

	public static void main(String[] args) {
		EntityFactory factory = EntityFactory.factoryDefault;
		File file = Tools.getFileToSave(args, 0, Config.pfGeneratorFileName(), Messages.pfGeneratorChooseFile());
		if (file == null) {
			return;
		}
		Organism organism = Tools.getOrganism(args, 1, Config.pfGeneratorOrganismNumber(),
			Messages.pfGeneratorChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}
		String seqSynonym = Tools.getString(args, 2, Config.pfGeneratorSeqSynonym(),
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

		String seqVersion = Tools.getString(args, 3, Config.pfGeneratorSeqVersion(),
			Messages.pfGeneratorChooseSeqVersion());
		if (seqVersion == null) {
			return;
		}

		boolean sequenceLocation = Tools.getBoolean(args, 4, Messages.pfGeneratorChooseSequenceLocation());

		Double ecThreshold = Tools.getDouble(args, 5, Config.pfEcThreshold(), Messages.pfGeneratorChooseEcThreshold());
		if (ecThreshold == null) {
			return;
		}

		Double goThreshold = Tools.getDouble(args, 6, Config.pfGoThreshold(), Messages.pfGeneratorChooseGoThreshold());
		if (goThreshold == null) {
			return;
		}

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

			PrintStream out = new PrintStream(new FileOutputStream(file, false));
			CycIdGenerator cycIdGenerator = new OrganismCycIdGenerator(organism);

			LocAndScores locAndScores = new LocAndScores();
			ScoreSystemCollection ecScoreSystemCollection = locAndScores.getScoreSystems("ec");
			ScoreSystemCollection goScoreSystemCollection = locAndScores.getScoreSystems("go");

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
								out.print(record.getName() + "\t");
								for (String ec : record.getECs()) {
									out.print(ec + ";");
								}
								out.print("\t");
								for (String go : record.getGOs()) {
									out.print(go + ";");
								}
								progress.completeStep();
							}
						}
					}
				}
			}
			out.flush();
			out.close();
			progress.finish(Messages.pfGeneratorFinalMsg(progress.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class LocAndScores implements ScoreSystemsContainer, LocContainer
	{
		@Override
		public ScoreSystemCollection getScoreSystems(String scoreName) {
			return PFFileConfig.getScoreSystems(scoreName);
		}

		@Override
		public List<String> getLocs(String locName) {
			return PFFileConfig.getLocs(locName);
		}

	}

}