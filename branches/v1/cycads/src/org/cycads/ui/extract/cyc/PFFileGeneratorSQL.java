/*
 * Created on 05/01/2009
 */
package org.cycads.ui.extract.cyc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.EntityFactorySQL;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.extract.cyc.CycIdGenerator;
import org.cycads.extract.cyc.CycRecordGenerator;
import org.cycads.extract.cyc.FileScoreSystem;
import org.cycads.extract.cyc.FixAndFileScoreSystem;
import org.cycads.extract.cyc.OrganismCycIdGenerator;
import org.cycads.extract.cyc.PFFileConfig;
import org.cycads.extract.cyc.PFFileStream;
import org.cycads.extract.cyc.PFFileCycRecordGenerator;
import org.cycads.extract.cyc.SimpleLocInterpreter;
import org.cycads.extract.cyc.SimpleScoreSystemCollection;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class PFFileGeneratorSQL {

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

		Double threshold = Tools.getDouble(args, 3, Messages.pfGeneratorChooseThreshold(),
			Config.pfGeneratorThreshold());
		if (threshold == null) {
			return;
		}
		Progress progress = new ProgressPrintInterval(System.out, Messages.pfGeneratorStepShowInterval());
		try {
			progress.init(Messages.pfGeneratorInitMsg(file.getPath()));
			Collection<Sequence< ? , ? , ? , ? , ? , ? >> seqs = organism.getSequences(seqVersion);
			Collection<Type> types = new ArrayList<Type>(1);
			types.add(factory.getAnnotationType(ParametersDefault.getCDSAnnotationTypeName()));
			PFFileStream pfFile = new PFFileStream(file, Config.pfGeneratorFileHeader());
			CycIdGenerator cycIdGenerator = new OrganismCycIdGenerator(organism);

			SimpleScoreSystemCollection scoreSystemCollection = new SimpleScoreSystemCollection();
			ArrayList<Pattern> patterns = PFFileConfig.getPatterns("pf.file.scoreAnnotation.methodName", null);
			ArrayList<String> values = PFFileConfig.getStrings("scoreAnnotation.value");
			ArrayList<String> fileNames = PFFileConfig.getStrings("scoreAnnotation.scoreNote.file");
			FileScoreSystem fileScoreSystem;
			String fileName;
			for (int i = 0; i < patterns.size(); i++) {
				if (fileNames.size() > 1) {
					fileName = fileNames.get(i);
				}
				else {
					fileName = null;
				}
				if (fileName != null && fileName.length() > 0) {
					fileScoreSystem = new FileScoreSystem(fileName);
				}
				else {
					fileScoreSystem = null;
				}
				scoreSystemCollection.addScoreSystem(patterns.get(i), new FixAndFileScoreSystem(
					Double.parseDouble(values.get(i)), fileScoreSystem));
			}

			CycRecordGenerator cycRecordGenerator = new PFFileCycRecordGenerator(threshold, cycIdGenerator,
				new SimpleLocInterpreter(), scoreSystemCollection);
			for (Sequence seq : seqs) {
				Collection<SubseqAnnotation< ? , ? , ? , ? , ? >> cdss = seq.getAnnotations(null, types, null);
				for (SubseqAnnotation< ? , ? , ? , ? , ? > cds : cdss) {
					pfFile.print(cycRecordGenerator.generate(cds));
					progress.completeStep();
				}
			}
			progress.finish(Messages.pfGeneratorFinalMsg(progress.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
