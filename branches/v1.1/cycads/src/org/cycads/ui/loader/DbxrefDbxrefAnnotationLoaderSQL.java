/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefAnnotation;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.association.AnnotationRecord;
import org.cycads.parser.association.DbnameTransformer;
import org.cycads.parser.association.DbxrefFactory;
import org.cycads.parser.association.LineRecordFileReader;
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
			br = new BufferedReader(new FileReader(file));
		}
		Organism< ? , ? , ? , ? , ? , ? > organism = Tools.getOrganism(args, 1,
			Config.dbxrefDbxrefAnnotationLoaderOrganismNumber(), Messages.generalChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}

		String methodName = Tools.getString(args, 2, Config.dbxrefDbxrefAnnotationLoaderMethodName(),
			Messages.generalChooseMethodName());
		if (methodName == null) {
			return;
		}
		AnnotationMethod method = factory.getAnnotationMethod(methodName);

		Integer dbxrefSourceColumnIndex = Tools.getInteger(args, 3,
			Config.dbxrefDbxrefAnnotationLoaderSourceColumnIndex(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseSourceColumnIndex());
		if (dbxrefSourceColumnIndex == null) {
			return;
		}

		String dbxrefSourceDBName = Tools.getString(args, 4, Config.dbxrefDbxrefAnnotationLoaderSourceDBName(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseSourceDBName());
		if (dbxrefSourceDBName == null) {
			return;
		}

		Integer dbxrefTargetColumnIndex = Tools.getInteger(args, 5,
			Config.dbxrefDbxrefAnnotationLoaderTargetColumnIndex(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseTargetColumnIndex());
		if (dbxrefTargetColumnIndex == null) {
			return;
		}

		String dbxrefTargetDBName = Tools.getString(args, 6, Config.dbxrefDbxrefAnnotationLoaderTargetDBName(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseTargetDBName());
		if (dbxrefTargetDBName == null) {
			return;
		}

		Integer scoreColumnIndex = Tools.getInteger(args, 7, Config.dbxrefDbxrefAnnotationLoaderScoreColumnIndex(),
			Messages.dbxrefDbxrefAnnotationLoaderChooseScoreColumnIndex());
		if (scoreColumnIndex == null) {
			return;
		}

		DbnameTransformer dbNameSource = new SimpleDbnameTransformer(dbxrefSourceDBName);
		DbnameTransformer dbNameTarget = new SimpleDbnameTransformer(dbxrefTargetDBName);

		DbxrefFactory sourceFactory = new DbxrefFactory(ParametersDefault.getDbxrefToStringSeparator(),
			Config.dbxrefDbxrefAnnotationSourceDelimiter(), dbNameSource, factory);

		DbxrefFactory targetFactory = new DbxrefFactory(ParametersDefault.getDbxrefToStringSeparator(),
			Config.dbxrefDbxrefAnnotationTargetDelimiter(), dbNameTarget, factory);

		SimpleAnnotationRecordFactory<Dbxref, Dbxref> recordFactory = new SimpleAnnotationRecordFactory<Dbxref, Dbxref>(
			dbxrefSourceColumnIndex, sourceFactory, dbxrefTargetColumnIndex, targetFactory, scoreColumnIndex);

		LineRecordFileReader<AnnotationRecord<Dbxref, Dbxref>> fileReader = new LineRecordFileReader<AnnotationRecord<Dbxref, Dbxref>>(
			br, Config.dbxrefDbxrefAnnotationColumnSeparator(), Config.dbxrefDbxrefAnnotationLineComment(),
			recordFactory);
		Progress progress = new ProgressPrintInterval(System.out,
			Messages.dbxrefDbxrefAnnotationLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		try {
			progress.init(Messages.dbxrefDbxrefAnnotationLoaderInitMsg(file.getPath()));

			//Colocar em una nova classe ????

			AnnotationRecord<Dbxref, Dbxref> record;

			while ((record = fileReader.read()) != null) {
				DbxrefAnnotation annot = record.getSource().addDbxrefAnnotation(method, record.getTarget());
				String score = record.getScore();
				if (annot != null && score != null) {
					annot.setScore(score);
				}
				//criar fakeannotation????
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			((EntityFactorySQL) factory).finish();
		}
	}
}
