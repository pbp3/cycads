/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.association.InputNameOverwrite;
import org.cycads.parser.association.LineRecordFileReader;
import org.cycads.parser.association.TypeNameTransformer;
import org.cycads.parser.association.factory.EntityObjectsRecordFactory;
import org.cycads.parser.association.factory.ObjectFactory;
import org.cycads.parser.association.factory.SimpleObjectsFactory;
import org.cycads.parser.association.factory.independent.IndependentDbxrefFactory;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressCount;
import org.cycads.ui.progress.ProgressPrintInterval;

public class SynonymLoaderSQL
{
	public static void main(String[] args) {
		EntityFactory factory = EntityFactory.factoryDefault;
		File file = Tools.getFileToOpen(args, 0, Config.synonymLoaderFileName(), Messages.generalChooseFileToLoad());
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

		Integer dbxrefSourceColumnIndex = Tools.getInteger(args, 1, Config.synonymLoaderSourceColumnIndex(),
			Messages.synonymLoaderChooseSourceColumnIndex());
		if (dbxrefSourceColumnIndex == null) {
			return;
		}

		String dbxrefSourceDBName = Tools.getString(args, 2, Config.synonymLoaderSourceDBName(),
			Messages.synonymLoaderChooseSourceDBName());
		if (dbxrefSourceDBName == null) {
			return;
		}

		Integer synonymColumnIndex = Tools.getInteger(args, 3, Config.synonymLoaderSynonymColumnIndex(),
			Messages.synonymLoaderChooseSynonymColumnIndex());
		if (synonymColumnIndex == null) {
			return;
		}

		String synonymDBName = Tools.getString(args, 4, Config.synonymLoaderSynonymDBName(),
			Messages.synonymLoaderChooseSynonymDBName());
		if (synonymDBName == null) {
			return;
		}

		ObjectFactory<Collection<Note>> notesFactory = null;

		TypeNameTransformer dbNameSource = new InputNameOverwrite(dbxrefSourceDBName);
		TypeNameTransformer dbNameSynonym = new InputNameOverwrite(synonymDBName);

		IndependentDbxrefFactory sourceFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameSource, factory);

		IndependentDbxrefFactory synonymFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameSynonym, factory);

		String columnDelimiter = Config.synonymLoaderSourceColumnDelimiter();
		String objectsDelimiter = Config.synonymLoaderSourcesDelimiter();
		String objectsSeparator = Config.synonymLoaderSourcesSeparator();

		ObjectFactory<Collection<Dbxref>> sourcesFactory = new SimpleObjectsFactory<Dbxref>(dbxrefSourceColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, sourceFactory);

		columnDelimiter = Config.synonymLoaderSynonymColumnDelimiter();
		objectsDelimiter = Config.synonymLoaderSynonymsDelimiter();
		objectsSeparator = Config.synonymLoaderSynonymsSeparator();

		ObjectFactory<Collection<Dbxref>> synonymsFactory = new SimpleObjectsFactory<Dbxref>(synonymColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, synonymFactory);

		String columnSeparator = Config.synonymLoaderColumnSeparator();
		String lineComment = Config.synonymLoaderLineComment();
		Pattern removeLinePattern = Config.synonymLoaderRemoveLineRegex();

		ObjectFactory<Collection<BasicEntity>> objectFactory = new EntityObjectsRecordFactory(factory, sourcesFactory,
			notesFactory, synonymsFactory, null);
		LineRecordFileReader<Collection<BasicEntity>> recordFileReader = new LineRecordFileReader<Collection<BasicEntity>>(
			br, columnSeparator, lineComment, removeLinePattern, objectFactory);

		Progress progress = new ProgressPrintInterval(System.out, Messages.synonymLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		progress.init(Messages.synonymLoaderInitMsg(file.getPath()));

		try {
			recordFileReader.readAll(progress, errorCount);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			progress.finish(Messages.synonymLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
			//			factory.finish();
		}
	}
}
