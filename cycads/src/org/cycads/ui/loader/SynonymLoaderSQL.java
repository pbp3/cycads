/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loader;

import java.io.File;
import java.io.IOException;

import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.sequence.Organism;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.parser.association.factory.SubseqOfAnnotToDbxrefParser;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressCount;
import org.cycads.ui.progress.ProgressPrintInterval;

public class SynonymLoaderSQL
{
	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToOpen(args, 0, Config.synonymLoaderFileName(), Messages.synonymLoaderChooseFile());
		if (file == null) {
			return;
		}
		Organism< ? > organism = Tools.getOrganism(args, 1, Config.synonymLoaderOrganismNumber(),
			Messages.synonymLoaderChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}

		Integer annotColumnIndex = Tools.getInteger(args, 2, Config.synonymLoaderAnnotColumnIndex(),
			Messages.synonymLoaderChooseAnnotColumnIndex());
		if (annotColumnIndex == null) {
			return;
		}

		String annotDBName = Tools.getString(args, 3, Config.synonymLoaderAnnotDBName(),
			Messages.synonymLoaderChooseAnnotDBName());
		if (annotDBName == null) {
			return;
		}

		Integer dbxrefColumnIndex = Tools.getInteger(args, 4, Config.synonymLoaderDbxrefColumnIndex(),
			Messages.synonymLoaderChooseDbxrefColumnIndex());
		if (dbxrefColumnIndex == null) {
			return;
		}

		String dbxrefDBName = Tools.getString(args, 5, Config.synonymLoaderDbxrefDBName(),
			Messages.synonymLoaderChooseDbxrefDBName());
		if (dbxrefDBName == null) {
			return;
		}

		Progress progress = new ProgressPrintInterval(System.out, Messages.synonymLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		try {
			progress.init(Messages.synonymLoaderInitMsg(file.getPath()));
			(new SubseqOfAnnotToDbxrefParser(factory, progress, organism, annotColumnIndex, annotDBName, dbxrefColumnIndex,
				dbxrefDBName, false, errorCount)).parse(file);
			progress.finish(Messages.synonymLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			((EntityFactorySQL) factory).finish();
		}
	}
}
