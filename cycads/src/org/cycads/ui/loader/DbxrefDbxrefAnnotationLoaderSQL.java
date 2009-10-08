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
import org.cycads.parser.association.DbnameTransformer;
import org.cycads.parser.association.DbxrefFactory;
import org.cycads.parser.association.Dbxrefs;
import org.cycads.parser.association.DbxrefsFactory;
import org.cycads.parser.association.LineRecordFileReader;
import org.cycads.parser.association.RecordFactory;
import org.cycads.parser.association.SimpleAnnotationRecord;
import org.cycads.parser.association.SimpleAnnotationRecordFactory;
import org.cycads.parser.association.SimpleDbnameTransformer;
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

		DbnameTransformer dbNameSource = new SimpleDbnameTransformer(dbxrefSourceDBName);
		DbnameTransformer dbNameTarget = new SimpleDbnameTransformer(dbxrefTargetDBName);

		DbxrefFactory sourceFactory = new DbxrefFactory(ParametersDefault.getDbxrefToStringSeparator(), dbNameSource,
			factory);

		DbxrefFactory targetFactory = new DbxrefFactory(ParametersDefault.getDbxrefToStringSeparator(), dbNameTarget,
			factory);

		DbxrefsFactory sourcesFactory = new DbxrefsFactory(Config.dbxrefDbxrefAnnotationLoaderSourceDbxrefsSeparator(),
			Config.dbxrefDbxrefAnnotationLoaderSourceDelimiter(), sourceFactory);

		DbxrefsFactory targetsFactory = new DbxrefsFactory(Config.dbxrefDbxrefAnnotationLoaderTargetDbxrefsSeparator(),
			Config.dbxrefDbxrefAnnotationLoaderTargetDelimiter(), targetFactory);

		RecordFactory<SimpleAnnotationRecord<Dbxrefs, Dbxrefs>> recordFactory = new SimpleAnnotationRecordFactory<Dbxrefs, Dbxrefs>(
			dbxrefSourceColumnIndex, sourcesFactory, dbxrefTargetColumnIndex, targetsFactory, scoreColumnIndex,
			methodColumnIndex, factory);

		LineRecordFileReader<SimpleAnnotationRecord<Dbxrefs, Dbxrefs>> fileReader = new LineRecordFileReader<SimpleAnnotationRecord<Dbxrefs, Dbxrefs>>(
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
