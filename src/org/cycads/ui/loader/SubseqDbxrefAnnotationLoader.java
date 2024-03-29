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

import org.cycads.entities.EntityFinder;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.association.InputNameOverwrite;
import org.cycads.parser.association.LineRecordFileReader;
import org.cycads.parser.association.TypeNameTransformer;
import org.cycads.parser.association.factory.AnnotationsRecordFactory;
import org.cycads.parser.association.factory.ConstantFactory;
import org.cycads.parser.association.factory.ExistedEntitiesFactoryBySynonym;
import org.cycads.parser.association.factory.ObjectFactory;
import org.cycads.parser.association.factory.SimpleObjectFactory;
import org.cycads.parser.association.factory.SimpleObjectsFactory;
import org.cycads.parser.association.factory.SubseqsFactory;
import org.cycads.parser.association.factory.independent.IndependentDbxrefFactory;
import org.cycads.parser.association.factory.independent.IndependentMethodFactory;
import org.cycads.parser.association.factory.independent.IndependentStringFactory;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressCount;
import org.cycads.ui.progress.ProgressPrintInterval;

public class SubseqDbxrefAnnotationLoader
{
	public static void main(String[] args) {
		EntityFactory factory = EntityFactory.factoryDefault;
		File file = Tools.getFileToOpen(args, 0, Config.subseqDbxrefAnnotationLoaderFileName(),
			Messages.generalChooseFileToLoad());
		if (file == null) {
			return;
		}
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}

		String methodName = Tools.getString(args, 1, Config.subseqDbxrefAnnotationLoaderMethodName(),
			Messages.generalChooseMethodName());
		if (methodName == null) {
			return;
		}
		//		AnnotationMethod method = factory.getAnnotationMethod(methodName);
		//		factory.setMethodDefault(factory.getAnnotationMethod(methodName));

		Integer subseqColumnIndex = Tools.getInteger(args, 2, Config.subseqDbxrefAnnotationLoaderAnnotColumnIndex(),
			Messages.subseqDbxrefAnnotationLoaderChooseSourceColumnIndex());
		if (subseqColumnIndex == null) {
			return;
		}

		String subseqDBName = Tools.getString(args, 3, Config.subseqDbxrefAnnotationLoaderAnnotDBName(),
			Messages.subseqDbxrefAnnotationLoaderChooseSourceDBName());
		if (subseqDBName == null) {
			return;
		}

		Integer dbxrefColumnIndex = Tools.getInteger(args, 4, Config.subseqDbxrefAnnotationLoaderDbxrefColumnIndex(),
			Messages.subseqDbxrefAnnotationLoaderChooseTargetColumnIndex());
		if (dbxrefColumnIndex == null) {
			return;
		}

		String dbxrefDBName = Tools.getString(args, 5, Config.subseqDbxrefAnnotationLoaderDbxrefDBName(),
			Messages.subseqDbxrefAnnotationLoaderChooseTargetDBName());
		if (dbxrefDBName == null) {
			return;
		}

		Integer scoreColumnIndex = Tools.getInteger(args, 6, Config.subseqDbxrefAnnotationLoaderScoreColumnIndex(),
			Messages.subseqDbxrefAnnotationLoaderChooseScoreColumnIndex());
		if (scoreColumnIndex == null) {
			return;
		}

		Integer methodColumnIndex = Tools.getIntegerOptional(args, 7,
			Config.subseqDbxrefAnnotationLoaderMethodColumnIndex());

		ObjectFactory<Collection<Annotation>> parentsFactory = null;
		ObjectFactory<Collection<Dbxref>> synonymsFactory = null;
		ObjectFactory<Collection<Note>> notesFactory = null;
		String scoreDelimiter = Config.subseqDbxrefAnnotationLoaderScoreDelimiter();
		ObjectFactory<String> scoreFactory = new SimpleObjectFactory<String>(scoreColumnIndex, scoreDelimiter,
			new IndependentStringFactory());

		String methodDelimiter = Config.subseqDbxrefAnnotationLoaderMethodDelimiter();
		List<Pattern> methodPatterns = Config.subseqDbxrefAnnotationLoaderMethodPatterns();
		List<String> methodNames = Config.subseqDbxrefAnnotationLoaderMethodNames();
		ObjectFactory<AnnotationMethod> methodFactory = new SimpleObjectFactory<AnnotationMethod>(methodColumnIndex,
			methodDelimiter, new IndependentMethodFactory<AnnotationMethod>((EntityMethodFactory) factory,
				methodPatterns, methodNames, factory.getAnnotationMethod(methodName)));

		Collection<String> associationTypeNames = Config.subseqDbxrefAnnotationLoaderAssocTypeNames();
		if (associationTypeNames == null || associationTypeNames.size() == 0) {
			associationTypeNames = new ArrayList<String>(1);
			associationTypeNames.add(ParametersDefault.getFunctionalAnnotationTypeName());
		}
		Collection<Type> associationTypes = new ArrayList<Type>(associationTypeNames.size());
		for (String associationTypeName : associationTypeNames) {
			associationTypes.add(factory.getType(associationTypeName));
		}
		ObjectFactory<Collection<Type>> typesFactory = new ConstantFactory<Collection<Type>>(associationTypes);

		TypeNameTransformer dbNameSource = new InputNameOverwrite(subseqDBName);
		TypeNameTransformer dbNameTarget = new InputNameOverwrite(dbxrefDBName);

		IndependentDbxrefFactory sourceFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameSource, factory);

		IndependentDbxrefFactory targetFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameTarget, factory);

		String columnDelimiter = Config.subseqDbxrefAnnotationLoaderSourceColumnDelimiter();
		String objectsDelimiter = Config.subseqDbxrefAnnotationLoaderSourcesDelimiter();
		String objectsSeparator = Config.subseqDbxrefAnnotationLoaderSourcesSeparator();

		ObjectFactory<Collection<Subsequence>> sourcesFactory = new SubseqsFactory(new ExistedEntitiesFactoryBySynonym(
			(EntityFinder) factory, new SimpleObjectsFactory<Dbxref>(subseqColumnIndex, columnDelimiter,
				objectsSeparator, objectsDelimiter, sourceFactory)));

		columnDelimiter = Config.subseqDbxrefAnnotationLoaderTargetColumnDelimiter();
		objectsDelimiter = Config.subseqDbxrefAnnotationLoaderTargetsDelimiter();
		objectsSeparator = Config.subseqDbxrefAnnotationLoaderTargetsSeparator();

		ObjectFactory<Collection<Dbxref>> targetsFactory = new SimpleObjectsFactory<Dbxref>(dbxrefColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, targetFactory);

		String columnSeparator = Config.subseqDbxrefAnnotationLoaderColumnSeparator();
		String lineComment = Config.subseqDbxrefAnnotationLoaderLineComment();
		Pattern removeLinePattern = Config.subseqDbxrefAnnotationLoaderRemoveLineRegex();

		ObjectFactory<Collection<Annotation<Subsequence, Dbxref>>> objectFactory = new AnnotationsRecordFactory<Subsequence, Dbxref>(
			(EntityAnnotationFactory) factory, sourcesFactory, targetsFactory, scoreFactory, methodFactory,
			typesFactory, notesFactory, synonymsFactory, parentsFactory);
		LineRecordFileReader<Collection<Annotation<Subsequence, Dbxref>>> recordFileReader = new LineRecordFileReader<Collection<Annotation<Subsequence, Dbxref>>>(
			br, columnSeparator, lineComment, removeLinePattern, objectFactory);

		Progress progress = new ProgressPrintInterval(System.out,
			Messages.subseqDbxrefAnnotationLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		progress.init(Messages.subseqDbxrefAnnotationLoaderInitMsg(file.getPath()));

		try {
			recordFileReader.readAll(progress, errorCount);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			progress.finish(Messages.subseqDbxrefAnnotationLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
			//			factory.finish();
		}
	}
}
