/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.association.InputNameOverwrite;
import org.cycads.parser.association.LineRecordFileReader;
import org.cycads.parser.association.TypeNameTransformer;
import org.cycads.parser.association.factory.AnnotationsRecordFactory;
import org.cycads.parser.association.factory.ConstantFactory;
import org.cycads.parser.association.factory.ObjectFactory;
import org.cycads.parser.association.factory.SimpleObjectFactory;
import org.cycads.parser.association.factory.SimpleObjectsFactory;
import org.cycads.parser.association.factory.independent.IndependentDbxrefFactory;
import org.cycads.parser.association.factory.independent.IndependentMethodFactory;
import org.cycads.parser.association.factory.independent.IndependentStringFactory;
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

		ObjectFactory<Collection<Annotation>> parentsFactory = null;
		ObjectFactory<Collection<Dbxref>> synonymsFactory = null;
		ObjectFactory<Collection<Note>> notesFactory = null;
		String scoreDelimiter = Config.dbxrefDbxrefAnnotationLoaderScoreDelimiter();
		ObjectFactory<String> scoreFactory = new SimpleObjectFactory<String>(scoreColumnIndex, scoreDelimiter,
			new IndependentStringFactory());

		String methodDelimiter = Config.dbxrefDbxrefAnnotationLoaderMethodDelimiter();
		List<Pattern> methodPatterns = Config.dbxrefDbxrefAnnotationLoaderMethodPatterns();
		List<String> methodNames = Config.dbxrefDbxrefAnnotationLoaderMethodNames();
		ObjectFactory<AnnotationMethod> methodFactory = new SimpleObjectFactory<AnnotationMethod>(methodColumnIndex,
			methodDelimiter, new IndependentMethodFactory<AnnotationMethod>((EntityMethodFactory) factory,
				methodPatterns, methodNames));

		Collection<String> associationTypeNames = Config.dbxrefDbxrefAnnotationLoaderAssocTypeNames();
		if (associationTypeNames == null || associationTypeNames.size() == 0) {
			associationTypeNames = new ArrayList<String>(1);
			associationTypeNames.add(ParametersDefault.getFunctionalAnnotationTypeName());
		}
		Collection<Type> associationTypes = new ArrayList<Type>(associationTypeNames.size());
		for (String associationTypeName : associationTypeNames) {
			associationTypes.add(factory.getType(associationTypeName));
		}
		ObjectFactory<Collection<Type>> typesFactory = new ConstantFactory<Collection<Type>>(associationTypes);

		TypeNameTransformer dbNameSource = new InputNameOverwrite(dbxrefSourceDBName);
		TypeNameTransformer dbNameTarget = new InputNameOverwrite(dbxrefTargetDBName);

		IndependentDbxrefFactory sourceFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameSource, factory);

		IndependentDbxrefFactory targetFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameTarget, factory);

		String columnDelimiter = Config.dbxrefDbxrefAnnotationLoaderSourceColumnDelimiter();
		String objectsDelimiter = Config.dbxrefDbxrefAnnotationLoaderSourcesDelimiter();
		String objectsSeparator = Config.dbxrefDbxrefAnnotationLoaderSourcesSeparator();

		ObjectFactory<Collection<Dbxref>> sourcesFactory = new SimpleObjectsFactory<Dbxref>(dbxrefSourceColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, sourceFactory);

		columnDelimiter = Config.dbxrefDbxrefAnnotationLoaderTargetColumnDelimiter();
		objectsDelimiter = Config.dbxrefDbxrefAnnotationLoaderTargetsDelimiter();
		objectsSeparator = Config.dbxrefDbxrefAnnotationLoaderTargetsSeparator();

		ObjectFactory<Collection<Dbxref>> targetsFactory = new SimpleObjectsFactory<Dbxref>(dbxrefTargetColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, targetFactory);

		String columnSeparator = Config.dbxrefDbxrefAnnotationLoaderColumnSeparator();
		String lineComment = Config.dbxrefDbxrefAnnotationLoaderLineComment();
		Pattern removeLinePattern = Config.dbxrefDbxrefAnnotationLoaderRemoveLineRegex();

		ObjectFactory<Collection<Annotation<Dbxref, Dbxref>>> objectFactory = new AnnotationsRecordFactory<Dbxref, Dbxref>(
			(EntityAnnotationFactory) factory, sourcesFactory, targetsFactory, scoreFactory, methodFactory,
			typesFactory, notesFactory, synonymsFactory, parentsFactory);
		LineRecordFileReader<Collection<Annotation<Dbxref, Dbxref>>> recordFileReader = new LineRecordFileReader<Collection<Annotation<Dbxref, Dbxref>>>(
			br, columnSeparator, lineComment, removeLinePattern, objectFactory);

		Progress progress = new ProgressPrintInterval(System.out,
			Messages.dbxrefDbxrefAnnotationLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		progress.init(Messages.dbxrefDbxrefAnnotationLoaderInitMsg(file.getPath()));

		try {
			recordFileReader.readAll(progress, errorCount);
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
