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

import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.note.Note;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.association.InputNameOverwrite;
import org.cycads.parser.association.LineRecordFileReader;
import org.cycads.parser.association.TypeNameTransformer;
import org.cycads.parser.association.factory.DbxrefsRecordFactory;
import org.cycads.parser.association.factory.ObjectFactory;
import org.cycads.parser.association.factory.SimpleObjectsFactory;
import org.cycads.parser.association.factory.independent.IndependentDbxrefFactory;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressCount;
import org.cycads.ui.progress.ProgressPrintInterval;

public class DbxrefSynonymLoaderSQL
{
	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToOpen(args, 0, Config.dbxrefSynonymLoaderFileName(),
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

		Integer dbxrefSourceColumnIndex = Tools.getInteger(args, 1, Config.dbxrefSynonymLoaderSourceColumnIndex(),
			Messages.dbxrefSynonymLoaderChooseSourceColumnIndex());
		if (dbxrefSourceColumnIndex == null) {
			return;
		}

		String dbxrefSourceDBName = Tools.getString(args, 2, Config.dbxrefSynonymLoaderSourceDBName(),
			Messages.dbxrefSynonymLoaderChooseSourceDBName());
		if (dbxrefSourceDBName == null) {
			return;
		}

		Integer dbxrefSynonymColumnIndex = Tools.getInteger(args, 3, Config.dbxrefSynonymLoaderSynonymColumnIndex(),
			Messages.dbxrefSynonymLoaderChooseSynonymColumnIndex());
		if (dbxrefSynonymColumnIndex == null) {
			return;
		}

		String dbxrefSynonymDBName = Tools.getString(args, 4, Config.dbxrefSynonymLoaderSynonymDBName(),
			Messages.dbxrefSynonymLoaderChooseSynonymDBName());
		if (dbxrefSynonymDBName == null) {
			return;
		}

		ObjectFactory<Collection<Note>> notesFactory = null;

		TypeNameTransformer dbNameSource = new InputNameOverwrite(dbxrefSourceDBName);
		TypeNameTransformer dbNameSynonym = new InputNameOverwrite(dbxrefSynonymDBName);

		IndependentDbxrefFactory sourceFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameSource, factory);

		IndependentDbxrefFactory synonymFactory = new IndependentDbxrefFactory(
			ParametersDefault.getDbxrefToStringSeparator(), dbNameSynonym, factory);

		String columnDelimiter = Config.dbxrefSynonymLoaderSourceColumnDelimiter();
		String objectsDelimiter = Config.dbxrefSynonymLoaderSourcesDelimiter();
		String objectsSeparator = Config.dbxrefSynonymLoaderSourcesSeparator();

		ObjectFactory<Collection<Dbxref>> sourcesFactory = new SimpleObjectsFactory<Dbxref>(dbxrefSourceColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, sourceFactory);

		columnDelimiter = Config.dbxrefSynonymLoaderSynonymColumnDelimiter();
		objectsDelimiter = Config.dbxrefSynonymLoaderSynonymsDelimiter();
		objectsSeparator = Config.dbxrefSynonymLoaderSynonymsSeparator();

		ObjectFactory<Collection<Dbxref>> synonymsFactory = new SimpleObjectsFactory<Dbxref>(dbxrefSynonymColumnIndex,
			columnDelimiter, objectsSeparator, objectsDelimiter, synonymFactory);

		String columnSeparator = Config.dbxrefSynonymLoaderColumnSeparator();
		String lineComment = Config.dbxrefSynonymLoaderLineComment();
		Pattern removeLinePattern = Config.dbxrefSynonymLoaderRemoveLineRegex();

		ObjectFactory<Collection<Dbxref>> objectFactory = new DbxrefsRecordFactory(sourcesFactory, notesFactory,
			synonymsFactory);
		LineRecordFileReader<Collection<Dbxref>> recordFileReader = new LineRecordFileReader<Collection<Dbxref>>(br,
			columnSeparator, lineComment, removeLinePattern, objectFactory);

		Progress progress = new ProgressPrintInterval(System.out, Messages.dbxrefSynonymLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		progress.init(Messages.dbxrefSynonymLoaderInitMsg(file.getPath()));

		try {
			recordFileReader.readAll(progress, errorCount);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			progress.finish(Messages.dbxrefSynonymLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
			factory.finish();
		}
	}
}
