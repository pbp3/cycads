/*
 * Created on 05/01/2009
 */
package org.cycads.ui.extract;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.cycads.entities.EntityFactorySQL;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.parser.gff3.GFF3Parser;
import org.cycads.parser.gff3.GeneralGFF3Handler;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class PFFileGeneratorSQL
{

	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToOpen(args, 0, Config.pfGeneratorFileName(), Messages.pfGeneratorChooseFile());
		if (file == null) {
			return;
		}
		Organism organism = Tools.getOrganism(args, 1, Config.pfGeneratorOrganismNumber(),
			Messages.pfGeneratorOrganismNumber(), Messages.pfGeneratorOrganismName(), factory);
		if (organism == null) {
			return;
		}
		String seqVersion = Tools.getString(args, 3, Messages.pfGeneratorChooseSeqVersion(),
			Config.pfGeneratorSeqVersion());
		if (seqVersion == null) {
			return;
		}
		Progress progress = new ProgressPrintInterval(System.out, Messages.pfGeneratorStepShowInterval());
		try {
			progress.init(Messages.pfGeneratorInitMsg(file.getPath()));
			Collection<Sequence> seqs = organism.getSequences(seqVersion);
			for (Sequence seq : seqs)
			{
				Collection<Annotation>
			}
			progress.finish(Messages.pfGeneratorFinalMsg(progress.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
