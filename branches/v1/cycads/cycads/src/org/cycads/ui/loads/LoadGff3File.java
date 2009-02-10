/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loads;

import java.io.File;
import java.io.IOException;

import org.cycads.entities.sequence.Organism;
import org.cycads.exceptions.LoadLineError;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.SimpleCacheCleanerController;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.loaders.gff3.GFF3Loader;
import org.cycads.loaders.gff3.GFF3Parser;
import org.cycads.ui.Arguments;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class LoadGff3File {
	public static void main(String[] args) {
		Arguments arguments = Arguments.getInstanceDefault();
		File file = arguments.getFileToOpen(args, 0, Config.gff3LoaderFileName(), Messages.gff3LoaderChooseFile());
		if (file == null) {
			return;
		}
		Organism organism = arguments.getOrganism(args, 1, Config.gff3LoaderOrganismNumber(),
			Messages.gff3ChooseOrganism());
		if (organism == null) {
			return;
		}
		String seqDBName = arguments.getString(args, 2, Messages.gff3LoaderChooseSeqDBName(),
			Config.gff3LoaderSeqDBName());
		if (seqDBName == null) {
			return;
		}
		boolean createSeq = arguments.getBoolean(args, 3, Messages.gff3LoaderCreateSequence());

		Progress progressGene = new ProgressPrintInterval(System.out,
			ParametersDefault.gff3LoaderStepShowIntervalGene(), Messages.gff3LoaderInitMsgGene(file.getPath()),
			Messages.gff3LoaderFinalMsgGene());
		Progress progressMRNA = new ProgressPrintInterval(System.out,
			ParametersDefault.gff3LoaderStepShowIntervalMRNA(), Messages.gff3LoaderInitMsgMRNA(file.getPath()),
			Messages.gff3LoaderFinalMsgMRNA());
		Progress progressCDS = new ProgressPrintInterval(System.out, ParametersDefault.gff3LoaderStepShowIntervalCDS(),
			Messages.gff3LoaderInitMsgCDS(file.getPath()), Messages.gff3LoaderFinalMsgCDS());
		try {
			(new GFF3Parser()).parse(file, new GFF3Loader(progressGene, progressMRNA, progressCDS,
				new SimpleCacheCleanerController(ParametersDefault.gff3LoaderStepCache(),
					arguments.getCacheCleanerSession()), organism, seqDBName, createSeq), null);
		}
		catch (IOException e) {
			BioJavaxSession.finishWithRollback();
			e.printStackTrace();
		}
		catch (LoadLineError e) {
			BioJavaxSession.finishWithRollback();
			e.printStackTrace();
		}
		// BioJavaxSession.finish();
	}
}
