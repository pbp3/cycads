/*
 * Created on 05/01/2009
 */
package org.cycads.ui.loader;

import java.io.File;
import java.io.IOException;

import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.sequence.Organism;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.parser.gff3.GFF3Parser;
import org.cycads.parser.gff3.GeneralGFF3Handler;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class GFF3Loader
{

	public static void main(String[] args) {
		EntityFactory factory = EntityFactory.factoryDefault;
		File file = Tools.getFileToOpen(args, 0, Config.gff3LoaderFileName(), Messages.gff3LoaderChooseFile());
		if (file == null) {
			return;
		}
		Organism< ? > organism = Tools.getOrCreateOrganism(args, 1, Config.gff3LoaderOrganismNumber(),
			Messages.gff3ChooseOrganismNumber(), Config.gff3LoaderOrganismName(), Messages.gff3ChooseOrganismName(),
			factory);
		if (organism == null) {
			return;
		}
		String seqDBName = Tools.getString(args, 2, Config.gff3LoaderSeqDBName(), Messages.gff3LoaderChooseSeqDBName());
		if (seqDBName == null) {
			return;
		}
		String seqVersion = Tools.getString(args, 3, Config.gff3LoaderSeqVersion(),
			Messages.gff3LoaderChooseSeqVersion());
		if (seqVersion == null) {
			return;
		}
		Progress progress = new ProgressPrintInterval(System.out, Messages.gff3LoaderStepShowInterval());
		try {
			progress.init(Messages.gff3LoaderInitMsg(file.getPath()));
			(new GFF3Parser()).parse(file, new GeneralGFF3Handler(factory, organism, seqDBName, seqVersion, progress));
			progress.finish(Messages.gff3LoaderFinalMsg(progress.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
