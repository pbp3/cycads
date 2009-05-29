/*
 * Created on 05/01/2009
 */
package org.cycads.ui.extract.cyc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cycads.entities.EntityFactorySQL;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
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
		Organism organism = Tools.getOrCreateOrganism(args, 1, Config.pfGeneratorOrganismNumber(),
			Messages.pfGeneratorChooseOrganismNumber(), Config.pfGeneratorOrganismName(),
			Messages.pfGeneratorChooseOrganismName(), factory);
		if (organism == null) {
			return;
		}
		String seqVersion = Tools.getString(args, 2, Messages.pfGeneratorChooseSeqVersion(),
			Config.pfGeneratorSeqVersion());
		if (seqVersion == null) {
			return;
		}

		boolean sequenceLocation = Tools.getBoolean(args, 3, Messages.pfGeneratorChooseSequenceLocation());

		Double ecThreshold = Tools.getDouble(args, 4, Messages.pfGeneratorChooseEcThreshold(), Config.pfEcThreshold());
		if (ecThreshold == null) {
			return;
		}

		Double goThreshold = Tools.getDouble(args, 5, Messages.pfGeneratorChooseGoThreshold(), Config.pfGoThreshold());
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
			Collection<Sequence< ? , ? , ? , ? , ? , ? >> seqs = organism.getSequences(seqVersion);
			Collection<Type> types = new ArrayList<Type>(1);
			types.add(factory.getAnnotationTypeCDS());
			PFFileStream pfFile = new PFFileStream(file, Config.pfGeneratorFileHeader(), sequenceLocation);
			CycIdGenerator cycIdGenerator = new OrganismCycIdGenerator(organism);

			ScoreSystemCollection ecScoreSystemCollection = PFFileConfig.getEcScoreSystems();
			ScoreSystemCollection goScoreSystemCollection = PFFileConfig.getGoScoreSystems();
			//			ScoreSystemCollection koScoreSystemCollection = PFFileConfig.getKoScoreSystems();

			Locs locs = new Locs();
			CycRecordGenerator cycRecordGenerator = new PFFileCycRecordGenerator(cycIdGenerator,
				new SimpleLocInterpreter(locs, locs), ecThreshold, ecScoreSystemCollection, goThreshold,
				goScoreSystemCollection);
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

	public static class Locs implements ScoreSystemsContainer, LocContainer
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
