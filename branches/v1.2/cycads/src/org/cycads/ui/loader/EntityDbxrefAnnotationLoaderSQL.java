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

import org.cycads.entities.BasicEntity;
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
import org.cycads.parser.association.factory.EntityObjectsRecordFactory;
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

public class EntityDbxrefAnnotationLoaderSQL
{
	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToOpen(args, 0, Config.entityDbxrefAnnotationLoaderFileName(),
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
		String methodName = Tools.getString(args, 1, Config.entityDbxrefAnnotationLoaderMethodName(),
			Messages.generalChooseMethodName());
		if (methodName == null) {
			return;
		}
		factory.setMethodDefault(factory.getAnnotationMethod(methodName));

		Integer dbxrefSourceColumnIndex = Tools.getInteger(args, 2,
			Config.entityDbxrefAnnotationLoaderSourceColumnIndex(),
			Messages.entityDbxrefAnnotationLoaderChooseSourceColumnIndex());
		if (dbxrefSourceColumnIndex == null) {
			return;
		}

		String dbxrefSourceDBName = Tools.getString(args, 3, Config.entityDbxrefAnnotationLoaderSourceDBName(),
			Messages.entityDbxrefAnnotationLoaderChooseSourceDBName());
		if (dbxrefSourceDBName == null) {
			return;
		}

		Integer dbxrefTargetColumnIndex = Tools.getInteger(args, 4,
			Config.entityDbxrefAnnotationLoaderTargetColumnIndex(),
			Messages.entityDbxrefAnnotationLoaderChooseTargetColumnIndex());
		if (dbxrefTargetColumnIndex == null) {
			return;
		}

		String dbxrefTargetDBName = Tools.getString(args, 5, Config.entityDbxrefAnnotationLoaderTargetDBName(),
			Messages.entityDbxrefAnnotationLoaderChooseTargetDBName());
		if (dbxrefTargetDBName == null) {
			return;
		}

		Integer scoreColumnIndex = Tools.getInteger(args, 6, Config.entityDbxrefAnnotationLoaderScoreColumnIndex(),
			Messages.entityDbxrefAnnotationLoaderChooseScoreColumnIndex());
		if (scoreColumnIndex == null) {
			return;
		}

		Integer methodColumnIndex = Tools.getIntegerOptional(args, 7,
			Config.entityDbxrefAnnotationLoaderMethodColumnIndex());

		ObjectFactory<Collection<Annotation>> parentsFactory = null;
		ObjectFactory<Collection<Dbxref>> synonymsFactory = null;
		ObjectFactory<Collection<Note>> notesFactory = null;
		String scoreDelimiter = Config.entityDbxrefAnnotationLoaderScoreDelimiter();
		ObjectFactory<String> scoreFactory = new SimpleObjectFactory<String>(scoreColumnIndex, scoreDelimiter,
			new IndependentStringFactory());

		String methodDelimiter = Config.entityDbxrefAnnotationLoaderMethodDelimiter();
		List<Pattern> methodPatterns = Config.entityDbxrefAnnotationLoaderMethodPatterns();
		List<String> methodNames = Config.entityDbxrefAnnotationLoaderMethodNames();
		ObjectFactory<AnnotationMethod> methodFactory = new SimpleObjectFactory<AnnotationMethod>(methodColumnIndex,
			methodDelimiter, new IndependentMethodFactory<AnnotationMethod>((EntityMethodFactory) factory,
				methodPatterns, methodNames));

		Collection<String> associationTypeNames = Config.entityDbxrefAnnotationLoaderAssocTypeNames();
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

		String columnDelimiter = Config.entityDbxrefAnnotationLoaderSourceColumnDelimiter();
		String objectsDelimiter = Config.entityDbxrefAnnotationLoaderSourcesDelimiter();
		String objectsSeparator = Config.entityDbxrefAnnotationLoaderSourcesSeparator();

		ObjectFactory<Collection<Dbxref>> synSourcesFactory = new SimpleObjectsFactory<Dbxref>(dbxrefSourceColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, sourceFactory);

		ObjectFactory<Collection<BasicEntity>> sourcesFactory = new EntityObjectsRecordFactory(factory,
			synSourcesFactory, null, null, null);

		columnDelimiter = Config.entityDbxrefAnnotationLoaderTargetColumnDelimiter();
		objectsDelimiter = Config.entityDbxrefAnnotationLoaderTargetsDelimiter();
		objectsSeparator = Config.entityDbxrefAnnotationLoaderTargetsSeparator();

		ObjectFactory<Collection<Dbxref>> targetsFactory = new SimpleObjectsFactory<Dbxref>(dbxrefTargetColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, targetFactory);

		String columnSeparator = Config.entityDbxrefAnnotationLoaderColumnSeparator();
		String lineComment = Config.entityDbxrefAnnotationLoaderLineComment();
		Pattern removeLinePattern = Config.entityDbxrefAnnotationLoaderRemoveLineRegex();

		ObjectFactory<Collection<Annotation<BasicEntity, Dbxref>>> objectFactory = new AnnotationsRecordFactory<BasicEntity, Dbxref>(
			(EntityAnnotationFactory) factory, sourcesFactory, targetsFactory, scoreFactory, methodFactory,
			typesFactory, notesFactory, synonymsFactory, parentsFactory);
		LineRecordFileReader<Collection<Annotation<BasicEntity, Dbxref>>> recordFileReader = new LineRecordFileReader<Collection<Annotation<BasicEntity, Dbxref>>>(
			br, columnSeparator, lineComment, removeLinePattern, objectFactory);

		Progress progress = new ProgressPrintInterval(System.out,
			Messages.entityDbxrefAnnotationLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		progress.init(Messages.entityDbxrefAnnotationLoaderInitMsg(file.getPath()));

		try {
			recordFileReader.readAll(progress, errorCount);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			progress.finish(Messages.entityDbxrefAnnotationLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
			factory.finish();
		}
	}
}
