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
import java.util.regex.Pattern;

import org.cycads.entities.EntityFinder;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.factory.EntityFeatureFactory;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
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

public class SubseqDbxrefAnnotationLoaderSQL
{
	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
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

		Organism< ? > organism = Tools.getOrganism(args, 1, Config.subseqDbxrefAnnotationLoaderOrganismNumber(),
			Messages.generalChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}

		String methodName = Tools.getString(args, 2, Config.subseqDbxrefAnnotationLoaderMethodName(),
			Messages.generalChooseMethodName());
		if (methodName == null) {
			return;
		}
		//		AnnotationMethod method = factory.getAnnotationMethod(methodName);
		factory.setMethodDefault(factory.getAnnotationMethod(methodName));

		Integer annotColumnIndex = Tools.getInteger(args, 3, Config.subseqDbxrefAnnotationLoaderAnnotColumnIndex(),
			Messages.subseqDbxrefAnnotationLoaderChooseSourceColumnIndex());
		if (annotColumnIndex == null) {
			return;
		}

		String annotDBName = Tools.getString(args, 4, Config.subseqDbxrefAnnotationLoaderAnnotDBName(),
			Messages.subseqDbxrefAnnotationLoaderChooseSourceDBName());
		if (annotDBName == null) {
			return;
		}

		Integer dbxrefColumnIndex = Tools.getInteger(args, 5, Config.subseqDbxrefAnnotationLoaderDbxrefColumnIndex(),
			Messages.subseqDbxrefAnnotationLoaderChooseTargetColumnIndex());
		if (dbxrefColumnIndex == null) {
			return;
		}

		String dbxrefDBName = Tools.getString(args, 6, Config.subseqDbxrefAnnotationLoaderDbxrefDBName(),
			Messages.subseqDbxrefAnnotationLoaderChooseTargetDBName());
		if (dbxrefDBName == null) {
			return;
		}

		Integer scoreColumnIndex = Tools.getInteger(args, 7, Config.subseqDbxrefAnnotationLoaderScoreColumnIndex(),
			Messages.subseqDbxrefAnnotationLoaderChooseScoreColumnIndex());
		if (scoreColumnIndex == null) {
			return;
		}

		Integer methodColumnIndex = Tools.getIntegerOptional(args, 8,
			Config.subseqDbxrefAnnotationLoaderMethodColumnIndex());

		ObjectFactory<Collection<Annotation>> parentsFactory = null;
		ObjectFactory<Collection<Dbxref>> synonymsFactory = null;
		ObjectFactory<Collection<Note>> notesFactory = null;
		String scoreDelimiter = Config.subseqDbxrefAnnotationLoaderScoreDelimiter();
		ObjectFactory<String> scoreFactory = new SimpleObjectFactory<String>(scoreColumnIndex, scoreDelimiter,
			new IndependentStringFactory());

		String methodDelimiter = Config.subseqDbxrefAnnotationLoaderMethodDelimiter();
		ObjectFactory<AnnotationMethod> methodFactory = new SimpleObjectFactory<AnnotationMethod>(methodColumnIndex,
			methodDelimiter, new IndependentMethodFactory<AnnotationMethod>((EntityMethodFactory) factory));

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

		TypeNameTransformer dbNameSource = new InputNameOverwrite(annotDBName);
		TypeNameTransformer dbNameTarget = new InputNameOverwrite(dbxrefDBName);

		IndependentDbxrefFactory sourceFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameSource, factory);

		IndependentDbxrefFactory targetFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameTarget, factory);

		String columnDelimiter = Config.dbxrefDbxrefAnnotationLoaderSourceColumnDelimiter();
		String objectsDelimiter = Config.dbxrefDbxrefAnnotationLoaderSourcesDelimiter();
		String objectsSeparator = Config.dbxrefDbxrefAnnotationLoaderSourcesSeparator();

		ObjectFactory<Collection<Subsequence>> sourcesFactory = new SubseqsFactory(new SimpleObjectsFactory<Dbxref>(
			annotColumnIndex, columnDelimiter, objectsSeparator, objectsDelimiter, sourceFactory),
			(EntityFinder) factory, organism, (EntityMethodFactory) factory, (EntityFeatureFactory) factory,
			(EntityTypeFactory) factory);

		columnDelimiter = Config.dbxrefDbxrefAnnotationLoaderTargetColumnDelimiter();
		objectsDelimiter = Config.dbxrefDbxrefAnnotationLoaderTargetsDelimiter();
		objectsSeparator = Config.dbxrefDbxrefAnnotationLoaderTargetsSeparator();

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
			factory.finish();
		}
	}
}
