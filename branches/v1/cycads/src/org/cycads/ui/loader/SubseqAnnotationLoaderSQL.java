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
import org.cycads.parser.AnnotToDbxrefFileParser;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressCount;
import org.cycads.ui.progress.ProgressPrintInterval;

public class SubseqAnnotationLoaderSQL
{
	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToOpen(args, 0, Config.subseqAnnotationLoaderFileName(),
			Messages.subseqAnnotationLoaderChooseFile());
		if (file == null) {
			return;
		}
		Organism< ? , ? , ? , ? , ? , ? > organism = Tools.getOrganism(args, 1,
			Config.subseqAnnotationLoaderOrganismNumber(), Messages.subseqAnnotationLoaderChooseOrganismNumber(),
			factory);
		if (organism == null) {
			return;
		}

		String methodName = Tools.getString(args, 2, Messages.subseqAnnotationLoaderChooseMethodName(),
			Config.subseqAnnotationLoaderMethodName());
		if (methodName == null) {
			return;
		}
		AnnotationMethod method = factory.getAnnotationMethod(methodName);

		Integer annotColumnIndex = Tools.getInteger(args, 3, Messages.subseqAnnotationLoaderChooseAnnotColumnIndex(),
			Config.subseqAnnotationLoaderAnnotColumnIndex());
		if (annotColumnIndex == null) {
			return;
		}

		String annotDBName = Tools.getString(args, 4, Messages.subseqAnnotationLoaderChooseAnnotDBName(),
			Config.subseqAnnotationLoaderAnnotDBName());
		if (annotDBName == null) {
			return;
		}

		Integer dbxrefColumnIndex = Tools.getInteger(args, 5, Messages.subseqAnnotationLoaderChooseDbxrefColumnIndex(),
			Config.subseqAnnotationLoaderDbxrefColumnIndex());
		if (dbxrefColumnIndex == null) {
			return;
		}

		String dbxrefDBName = Tools.getString(args, 6, Messages.subseqAnnotationLoaderChooseDbxrefDBName(),
			Config.subseqAnnotationLoaderDbxrefDBName());
		if (dbxrefDBName == null) {
			return;
		}

		Integer scoreColumnIndex = Tools.getInteger(args, 7, Messages.subseqAnnotationLoaderChooseScoreColumnIndex(),
			Config.subseqAnnotationLoaderScoreColumnIndex());
		if (scoreColumnIndex == null) {
			return;
		}

		Progress progress = new ProgressPrintInterval(System.out, Messages.subseqAnnotationLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		try {
			progress.init(Messages.subseqAnnotationLoaderInitMsg(file.getPath()));
			(new AnnotToDbxrefFileParser(factory, progress, method, organism, annotColumnIndex, annotDBName,
				dbxrefColumnIndex, dbxrefDBName, scoreColumnIndex, errorCount)).parse(file);
			progress.finish(Messages.subseqAnnotationLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			((EntityFactorySQL) factory).finish();
		}
	}
}
