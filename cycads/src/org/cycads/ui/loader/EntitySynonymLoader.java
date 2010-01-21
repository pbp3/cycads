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

public class EntitySynonymLoader
{
	public static void main(String[] args) {
		EntityFactory factory = EntityFactory.factoryDefault;
		File file = Tools.getFileToOpen(args, 0, Config.entitySynonymLoaderFileName(),
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

		Integer dbxrefSourceColumnIndex = Tools.getInteger(args, 1, Config.entitySynonymLoaderSourceColumnIndex(),
			Messages.entitySynonymLoaderChooseSourceColumnIndex());
		if (dbxrefSourceColumnIndex == null) {
			return;
		}

		String dbxrefSourceDBName = Tools.getString(args, 2, Config.entitySynonymLoaderSourceDBName(),
			Messages.entitySynonymLoaderChooseSourceDBName());
		if (dbxrefSourceDBName == null) {
			return;
		}

		Integer synonymColumnIndex = Tools.getInteger(args, 3, Config.entitySynonymLoaderSynonymColumnIndex(),
			Messages.entitySynonymLoaderChooseSynonymColumnIndex());
		if (synonymColumnIndex == null) {
			return;
		}

		String synonymDBName = Tools.getString(args, 4, Config.entitySynonymLoaderSynonymDBName(),
			Messages.entitySynonymLoaderChooseSynonymDBName());
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

		String columnDelimiter = Config.entitySynonymLoaderSourceColumnDelimiter();
		String objectsDelimiter = Config.entitySynonymLoaderSourcesDelimiter();
		String objectsSeparator = Config.entitySynonymLoaderSourcesSeparator();

		ObjectFactory<Collection<Dbxref>> sourcesFactory = new SimpleObjectsFactory<Dbxref>(dbxrefSourceColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, sourceFactory);

		columnDelimiter = Config.entitySynonymLoaderSynonymColumnDelimiter();
		objectsDelimiter = Config.entitySynonymLoaderSynonymsDelimiter();
		objectsSeparator = Config.entitySynonymLoaderSynonymsSeparator();

		ObjectFactory<Collection<Dbxref>> synonymsFactory = new SimpleObjectsFactory<Dbxref>(synonymColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, synonymFactory);

		String columnSeparator = Config.entitySynonymLoaderColumnSeparator();
		String lineComment = Config.entitySynonymLoaderLineComment();
		Pattern removeLinePattern = Config.entitySynonymLoaderRemoveLineRegex();

		ObjectFactory<Collection<BasicEntity>> objectFactory = new EntityObjectsRecordFactory(factory, sourcesFactory,
			notesFactory, synonymsFactory, null);
		LineRecordFileReader<Collection<BasicEntity>> recordFileReader = new LineRecordFileReader<Collection<BasicEntity>>(
			br, columnSeparator, lineComment, removeLinePattern, objectFactory);

		Progress progress = new ProgressPrintInterval(System.out, Messages.entitySynonymLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		progress.init(Messages.entitySynonymLoaderInitMsg(file.getPath()));

		try {
			recordFileReader.readAll(progress, errorCount);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			progress.finish(Messages.entitySynonymLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
			//			factory.finish();
		}
	}
}
