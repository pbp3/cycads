/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.cycads.entities.EntityFactorySQL;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefAnnotation;
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
		File file = Tools.getFileToOpen(args, 0, Config.subseqAnnotationLoaderFileName(),
			Messages.subseqAnnotationLoaderChooseFile());
		BufferedReader br;
		if (file == null) {
			return;
		}
		else {
			br = new BufferedReader(new FileReader(file));
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

		DbnameTransformer dbNameSource = new SimpleDbnameTransformer(annotDBName);
		DbnameTransformer dbNameTarget = new SimpleDbnameTransformer(dbxrefDBName);

		DbxrefFactory sourceFactory = new DbxrefFactory(ParametersDefault.getDbxrefToStringSeparator(),
			ParametersDefault.dbxrefDbxrefAnnotationSourceDelimiter(), dbNameSource, factory);

		DbxrefFactory targetFactory = new DbxrefFactory(ParametersDefault.getDbxrefToStringSeparator(),
			ParametersDefault.dbxrefDbxrefAnnotationTargetDelimiter(), dbNameTarget, factory);

		SimpleAnnotationRecordFactory<Dbxref, Dbxref> recordFactory = new SimpleAnnotationRecordFactory<Dbxref, Dbxref>(
			annotColumnIndex, sourceFactory, dbxrefColumnIndex, targetFactory, scoreColumnIndex);

		LineRecordFileReader<AnnotationRecord<Dbxref, Dbxref>> fileReader = new LineRecordFileReader<AnnotationRecord<Dbxref, Dbxref>>(
			br, ParametersDefault.dbxrefDbxrefAnnotationColumnSeparator(),
			ParametersDefault.dbxrefDbxrefAnnotationLineComment(), recordFactory);
		Progress progress = new ProgressPrintInterval(System.out, Messages.subseqAnnotationLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		try {
			progress.init(Messages.subseqAnnotationLoaderInitMsg(file.getPath()));

			AnnotationRecord<Dbxref, Dbxref> record;

			while ((record = fileReader.read()) != null) {
				DbxrefAnnotation annot = record.getSource().addDbxrefAnnotation(method, record.getTarget());
				String score = record.getScore();
				if (annot != null && score != null) {
					annot.addNote(ParametersDefault.getScoreAnnotationNoteTypeName(), score);
				}
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
