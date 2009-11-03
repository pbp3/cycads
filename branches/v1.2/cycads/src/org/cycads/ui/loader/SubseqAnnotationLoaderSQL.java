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
import org.cycads.entities.factory.EntityDbxrefFactory;
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
import org.cycads.parser.association.factory.AnnotationsRecordFactory;
import org.cycads.parser.association.factory.ConstantFactory;
import org.cycads.parser.association.factory.ObjectFactory;
import org.cycads.parser.association.factory.SimpleObjectFactory;
import org.cycads.parser.association.factory.SimpleObjectsFactory;
import org.cycads.parser.association.factory.SubseqsFactory;
import org.cycads.parser.association.factory.independent.IndependentDbxrefFactory;
import org.cycads.parser.association.factory.independent.IndependentStringFactory;
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
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}

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

		Pattern removeLinePattern = Pattern.compile(ParametersDefault.annotToDbxrefRemoveLineRegex());

		ObjectFactory<Collection<Annotation>> parentsFactory = null;
		ObjectFactory<Collection<Dbxref>> synonymsFactory = null;
		ObjectFactory<Collection<Note>> notesFactory = null;
		String scoreDelimiter = ParametersDefault.annotToDbxrefFileTextDelimiter();
		ObjectFactory<String> scoreFactory = new SimpleObjectFactory<String>(scoreColumnIndex, scoreDelimiter,
			new IndependentStringFactory());
		ObjectFactory<AnnotationMethod> methodFactory = new ConstantFactory<AnnotationMethod>(method);
		Collection<Type> associationTypes = new ArrayList<Type>(1);
		associationTypes.add(factory.getType(ParametersDefault.getFunctionalAnnotationTypeName()));
		ObjectFactory<Collection<Type>> typesFactory = new ConstantFactory<Collection<Type>>(associationTypes);

		String dbxrefsAnnotDelimiter = ParametersDefault.annotToDbxrefFileTextDelimiter();
		String dbxrefAnnotDelimiter = ParametersDefault.annotToDbxrefFileTextDelimiter();
		String dbxrefsAnnotSeparator = ParametersDefault.annotToDbxrefFileAnnotsSeparator();

		ObjectFactory<Collection<Subsequence>> sourcesFactory = new SubseqsFactory(new SimpleObjectsFactory<Dbxref>(
			annotColumnIndex, dbxrefsAnnotDelimiter, dbxrefsAnnotSeparator, dbxrefAnnotDelimiter,
			new IndependentDbxrefFactory<Dbxref>(ParametersDefault.getDbxrefToStringSeparator(),
				new InputNameOverwrite(annotDBName), (EntityDbxrefFactory) factory)), (EntityFinder) factory, organism,
			(EntityMethodFactory) factory, (EntityFeatureFactory) factory, (EntityTypeFactory) factory);

		String dbxrefsDelimiter = ParametersDefault.annotToDbxrefFileTextDelimiter();
		String dbxrefDelimiter = ParametersDefault.annotToDbxrefFileTextDelimiter();
		String dbxrefsSeparator = ParametersDefault.annotToDbxrefFileDbxrefsSeparator();

		ObjectFactory<Collection<Dbxref>> targetsFactory = new SimpleObjectsFactory<Dbxref>(dbxrefColumnIndex,
			dbxrefsDelimiter, dbxrefsSeparator, dbxrefDelimiter, new IndependentDbxrefFactory<Dbxref>(
				ParametersDefault.getDbxrefToStringSeparator(), new InputNameOverwrite(dbxrefDBName),
				(EntityDbxrefFactory) factory));

		String columnSeparator = ParametersDefault.annotToDbxrefFileSeparator();
		String lineComment = ParametersDefault.annotToDbxrefFileComment();

		ObjectFactory<Collection<Annotation<Subsequence, Dbxref>>> objectFactory = new AnnotationsRecordFactory<Subsequence, Dbxref>(
			(EntityAnnotationFactory) factory, sourcesFactory, targetsFactory, scoreFactory, methodFactory,
			typesFactory, notesFactory, synonymsFactory, parentsFactory);
		LineRecordFileReader<Collection<Annotation<Subsequence, Dbxref>>> recordFileReader = new LineRecordFileReader<Collection<Annotation<Subsequence, Dbxref>>>(
			br, columnSeparator, lineComment, removeLinePattern, objectFactory);

		Progress progress = new ProgressPrintInterval(System.out, Messages.subseqAnnotationLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();

		try {
			progress.init(Messages.subseqAnnotationLoaderInitMsg(file.getPath()));
			recordFileReader.readAll(progress, errorCount);
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
