/*
 * Created on 05/01/2009
 */
package org.cycads.ui.extract.cyc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.extract.cyc.CycIdGenerator;
import org.cycads.extract.cyc.CycRecord;
import org.cycads.extract.cyc.CycRecordGenerator;
import org.cycads.extract.cyc.LocContainer;
import org.cycads.extract.cyc.OrganismCycIdGenerator;
import org.cycads.extract.cyc.PFFileConfig;
import org.cycads.extract.cyc.PFFileCycRecordGenerator;
import org.cycads.extract.cyc.PFFileStream;
import org.cycads.extract.cyc.ScoreSystemCollection;
import org.cycads.extract.cyc.ScoreSystemsContainer;
import org.cycads.extract.cyc.SimpleLocInterpreter;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class PFFileGeneratorSQL
{

	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
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

		//		Double koThreshold = Tools.getDouble(args, 6, Messages.pfGeneratorChooseKoThreshold(), Config.pfKoThreshold());
		//		if (koThreshold == null) {
		//			return;
		//		}

		Progress progress = new ProgressPrintInterval(System.out, Messages.pfGeneratorStepShowInterval());
		try {
			progress.init(Messages.pfGeneratorInitMsg(file.getPath()));
			Collection<Sequence< ? , ? , ? , ? , ? , ? >> seqs;
			if (seqDbname == null) {
				seqs = organism.getSequences(seqVersion);
			}
			else {
				Dbxref synonym = factory.getDbxref(seqDbname, seqAccession);
				seqs = organism.getSequences(synonym, seqVersion);
			}

			Collection<Type> types = new ArrayList<Type>(1);
			types.add(factory.getAnnotationTypeCDS());
			types.add(factory.getAnnotationType("TRNA"));
			PFFileStream pfFile = new PFFileStream(file, Config.pfGeneratorFileHeader(), sequenceLocation);
			CycIdGenerator cycIdGenerator = new OrganismCycIdGenerator(organism);

			LocAndScores locAndScores = new LocAndScores();
			ScoreSystemCollection ecScoreSystemCollection = locAndScores.getScoreSystems("ec");
			ScoreSystemCollection goScoreSystemCollection = locAndScores.getScoreSystems("go");

			CycRecordGenerator cycRecordGenerator = new PFFileCycRecordGenerator(cycIdGenerator,
				new SimpleLocInterpreter(locAndScores, locAndScores), ecThreshold, ecScoreSystemCollection,
				goThreshold, goScoreSystemCollection);
			for (Sequence seq : seqs) {
				Collection<SubseqAnnotation< ? , ? , ? , ? , ? >> cdss = seq.getAnnotations(null, types, null);
				for (SubseqAnnotation< ? , ? , ? , ? , ? > cds : cdss) {
					CycRecord record = cycRecordGenerator.generate(cds);
					if (record != null) {
						pfFile.print(record);
						progress.completeStep();
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
		public ScoreSystemCollection getScoreSystems(String scoreName) {
			return PFFileConfig.getScoreSystems(scoreName);
		}

		@Override
		public List<String> getLocs(String locName) {
			return PFFileConfig.getLocs(locName);
		}

	}

}
