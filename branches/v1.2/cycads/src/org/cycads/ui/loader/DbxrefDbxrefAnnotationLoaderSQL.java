/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.association.TypeNameTransformer;
import org.cycads.parser.association.IndependentDbxrefFactory;
import org.cycads.parser.association.DbxrefsField;
import org.cycads.parser.association.DbxrefsFactory;
import org.cycads.parser.association.LineRecordFileReader;
import org.cycads.parser.association.ObjectFactory;
import org.cycads.parser.association.SimpleAnnotation;
import org.cycads.parser.association.SimpleAnnotationRecordFactory;
import org.cycads.parser.association.InputNameOverwrite;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressCount;
import org.cycads.ui.progress.ProgressPrintInterval;

public class DbxrefDbxrefAnnotationLoaderSQL
{
	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToOpen(args, 0, Config.dbxrefDbxrefAnnotationLoaderFileName(),
			Messages.generalChooseFileToLoad());
		BufferedReader br;
		if (file == null) {
			return;
		}
		else {
			try {
				br = new BufferedReader(new FileReader(file));
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
		}
		String methodName = Tools.getString(args, 1, Config.dbxrefDbxrefAnnotationLoaderMethodName(),
			Messages.generalChooseMethodName());
		if (methodName == null) {
			return;
		}
		factory.setMethodDefault(factory.getAnnotationMethod(methodName));

		Integer dbxrefSourceColumnIndex = Tools.getInteger(args, 2,
			Config.dbxrefDbxrefAnnotationLoaderSourceColumnIndex(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseSourceColumnIndex());
		if (dbxrefSourceColumnIndex == null) {
			return;
		}

		String dbxrefSourceDBName = Tools.getString(args, 3, Config.dbxrefDbxrefAnnotationLoaderSourceDBName(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseSourceDBName());
		if (dbxrefSourceDBName == null) {
			return;
		}

		Integer dbxrefTargetColumnIndex = Tools.getInteger(args, 4,
			Config.dbxrefDbxrefAnnotationLoaderTargetColumnIndex(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseTargetColumnIndex());
		if (dbxrefTargetColumnIndex == null) {
			return;
		}

		String dbxrefTargetDBName = Tools.getString(args, 5, Config.dbxrefDbxrefAnnotationLoaderTargetDBName(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseTargetDBName());
		if (dbxrefTargetDBName == null) {
			return;
		}

		Integer scoreColumnIndex = Tools.getInteger(args, 6, Config.dbxrefDbxrefAnnotationLoaderScoreColumnIndex(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseScoreColumnIndex());
		if (scoreColumnIndex == null) {
			return;
		}

		Integer methodColumnIndex = Tools.getIntegerOptional(args, 7,
			Config.dbxrefDbxrefAnnotationLoaderMethodColumnIndex());

		TypeNameTransformer dbNameSource = new InputNameOverwrite(dbxrefSourceDBName);
		TypeNameTransformer dbNameTarget = new InputNameOverwrite(dbxrefTargetDBName);

		IndependentDbxrefFactory sourceFactory = new IndependentDbxrefFactory(ParametersDefault.getDbxrefToStringSeparator(), dbNameSource,
			factory);

		IndependentDbxrefFactory targetFactory = new IndependentDbxrefFactory(ParametersDefault.getDbxrefToStringSeparator(), dbNameTarget,
			factory);

		DbxrefsFactory sourcesFactory = new DbxrefsFactory(Config.dbxrefDbxrefAnnotationLoaderSourceDbxrefsSeparator(),
			Config.dbxrefDbxrefAnnotationLoaderSourceDelimiter(), sourceFactory);

		DbxrefsFactory targetsFactory = new DbxrefsFactory(Config.dbxrefDbxrefAnnotationLoaderTargetDbxrefsSeparator(),
			Config.dbxrefDbxrefAnnotationLoaderTargetDelimiter(), targetFactory);

		ObjectFactory<SimpleAnnotation<DbxrefsField, DbxrefsField>> recordFactory = new SimpleAnnotationRecordFactory<DbxrefsField, DbxrefsField>(
			dbxrefSourceColumnIndex, sourcesFactory, dbxrefTargetColumnIndex, targetsFactory, scoreColumnIndex,
			methodColumnIndex, factory);

		LineRecordFileReader<SimpleAnnotation<DbxrefsField, DbxrefsField>> fileReader = new LineRecordFileReader<SimpleAnnotation<DbxrefsField, DbxrefsField>>(
			br, Config.dbxrefDbxrefAnnotationLoaderColumnSeparator(), Config.dbxrefDbxrefAnnotationLoaderLineComment(),
			Config.dbxrefDbxrefAnnotationLoaderLineIgnorePattern(), recordFactory);
		Progress progress = new ProgressPrintInterval(System.out,
			Messages.dbxrefDbxrefAnnotationLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		progress.init(Messages.dbxrefDbxrefAnnotationLoaderInitMsg(file.getPath()));

		try {
			Loaders.loadDbxrefsDbxrefsAnnotation(fileReader, progress, errorCount);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			progress.finish(Messages.dbxrefDbxrefAnnotationLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
			factory.finish();
		}
	}
}
