/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loader;

import java.io.File;
import java.io.IOException;

import org.cycads.entities.EntityFactorySQL;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.sequence.Organism;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.parser.CDSToDbxrefFileParser;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressCount;
import org.cycads.ui.progress.ProgressPrintInterval;

public class CDSToDbxrefLoaderSQL {
	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToOpen(args, 0, Config.cdsToDbxrefLoaderFileName(),
			Messages.cdsToDbxrefLoaderChooseFile());
		if (file == null) {
			return;
		}
		Organism< ? , ? , ? , ? , ? , ? > organism = Tools.getOrganism(args, 1,
			Config.cdsToDbxrefLoaderOrganismNumber(), Messages.cdsToDbxrefLoaderChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}

		String methodName = Tools.getString(args, 2, Messages.cdsToDbxrefLoaderChooseMethodName(),
			Config.cdsToDbxrefLoaderMethodName());
		if (methodName == null) {
			return;
		}
		AnnotationMethod method = factory.getAnnotationMethod(methodName);

		Integer cdsColumnIndex = Tools.getInteger(args, 3, Messages.cdsToDbxrefLoaderChooseCDSColumnIndex(),
			Config.cdsToDbxrefLoaderCDSColumnIndex());
		if (cdsColumnIndex == null) {
			return;
		}

		String cdsDBName = Tools.getString(args, 4, Messages.cdsToDbxrefLoaderChooseCDSDBName(),
			Config.cdsToDbxrefCDSDBName());
		if (cdsDBName == null) {
			return;
		}

		Integer dbxrefColumnIndex = Tools.getInteger(args, 5, Messages.cdsToDbxrefLoaderChooseDbxrefColumnIndex(),
			Config.cdsToDbxrefLoaderDbxrefColumnIndex());
		if (dbxrefColumnIndex == null) {
			return;
		}

		String dbxrefDBName = Tools.getString(args, 6, Messages.cdsToDbxrefLoaderChooseDbxrefDBName(),
			Config.cdsToDbxrefLoaderDbxrefDBName());
		if (dbxrefDBName == null) {
			return;
		}

		Integer scoreColumnIndex = Tools.getInteger(args, 7, Messages.cdsToDbxrefLoaderChooseScoreColumnIndex(),
			Config.cdsToDbxrefLoaderScoreColumnIndex());
		if (scoreColumnIndex == null) {
			return;
		}

		Progress progress = new ProgressPrintInterval(System.out, Messages.cdsToDbxrefLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		try {
			progress.init(Messages.cdsToDbxrefLoaderInitMsg(file.getPath()));
			(new CDSToDbxrefFileParser(factory, progress, method, organism, cdsColumnIndex, cdsDBName,
				dbxrefColumnIndex, dbxrefDBName, scoreColumnIndex, errorCount)).parse(file);
			progress.finish(Messages.cdsToDbxrefLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			((EntityFactorySQL) factory).finish();
		}
	}
}
