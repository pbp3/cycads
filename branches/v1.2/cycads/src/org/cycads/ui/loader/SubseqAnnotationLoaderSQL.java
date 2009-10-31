/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.EntityObject;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.SubseqOfAnnotToDbxrefParser;
import org.cycads.parser.association.LineRecordFileReader;
import org.cycads.parser.association.factory.AnnotationsRecordFactory;
import org.cycads.parser.association.factory.ObjectFactory;
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
		BufferedReader br = new BufferedReader(new FileReader(f));

		Organism< ? > organism = Tools.getOrganism(args, 1, Config.subseqAnnotationLoaderOrganismNumber(),
			Messages.subseqAnnotationLoaderChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}

		String methodName = Tools.getString(args, 2, Config.subseqAnnotationLoaderMethodName(),
			Messages.subseqAnnotationLoaderChooseMethodName());
		if (methodName == null) {
			return;
		}
		AnnotationMethod method = factory.getAnnotationMethod(methodName);

		Integer annotColumnIndex = Tools.getInteger(args, 3, Config.subseqAnnotationLoaderAnnotColumnIndex(),
			Messages.subseqAnnotationLoaderChooseAnnotColumnIndex());
		if (annotColumnIndex == null) {
			return;
		}

		String annotDBName = Tools.getString(args, 4, Config.subseqAnnotationLoaderAnnotDBName(),
			Messages.subseqAnnotationLoaderChooseAnnotDBName());
		if (annotDBName == null) {
			return;
		}

		Integer dbxrefColumnIndex = Tools.getInteger(args, 5, Config.subseqAnnotationLoaderDbxrefColumnIndex(),
			Messages.subseqAnnotationLoaderChooseDbxrefColumnIndex());
		if (dbxrefColumnIndex == null) {
			return;
		}

		String dbxrefDBName = Tools.getString(args, 6, Config.subseqAnnotationLoaderDbxrefDBName(),
			Messages.subseqAnnotationLoaderChooseDbxrefDBName());
		if (dbxrefDBName == null) {
			return;
		}

		Integer scoreColumnIndex = Tools.getInteger(args, 7, Config.subseqAnnotationLoaderScoreColumnIndex(),
			Messages.subseqAnnotationLoaderChooseScoreColumnIndex());
		if (scoreColumnIndex == null) {
			return;
		}

		String removeLineRegex = ParametersDefault.annotToDbxrefRemoveLineRegex();
		Pattern removeLinePattern = Pattern.compile(removeLineRegex);

		Progress progress = new ProgressPrintInterval(System.out, Messages.subseqAnnotationLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();

		ObjectFactory<Collection<Annotation>> parentsFactory = null;
		ObjectFactory<Collection<Dbxref>> synonymsFactory = null;
		ObjectFactory<Collection<Note>> notesFactory = null;
		ObjectFactory<String> scoreFactory;
		ObjectFactory<AnnotationMethod> methodFactory;
		ObjectFactory<Collection<Dbxref>> targetsFactory;
		ObjectFactory<Collection<Subsequence>> sourcesFactory;
		ObjectFactory<Collection<Type>> typesFactory;

		ObjectFactory<Collection<Annotation<Subsequence, Dbxref>>> objectFactory = new AnnotationsRecordFactory<Subsequence, Dbxref>(
			(EntityAnnotationFactory<EntityObject>) factory, sourcesFactory, targetsFactory, scoreFactory,
			methodFactory, typesFactory, notesFactory, synonymsFactory, parentsFactory);
		LineRecordFileReader<Collection<Annotation<Subsequence, Dbxref>>> recordFileReader = new LineRecordFileReader<Collection<Annotation<Subsequence, Dbxref>>>(
			br, ParametersDefault.annotToDbxrefFileSeparator(), ParametersDefault.annotToDbxrefFileComment(),
			removeLinePattern, objectFactory);

		try {
			progress.init(Messages.subseqAnnotationLoaderInitMsg(file.getPath()));
			(new SubseqOfAnnotToDbxrefParser(factory, progress, method, organism, annotColumnIndex, annotDBName,
				dbxrefColumnIndex, dbxrefDBName, scoreColumnIndex, true, errorCount)).parse(file);
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
