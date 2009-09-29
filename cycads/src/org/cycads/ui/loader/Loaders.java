/*
 * Created on 29/09/2009
 */
package org.cycads.ui.loader;

import java.io.IOException;
import java.util.List;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefAnnotable;
import org.cycads.entities.annotation.DbxrefAnnotation;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.FileParserError;
import org.cycads.parser.association.AnnotationRecord;
import org.cycads.parser.association.AssociationRecord;
import org.cycads.parser.association.Dbxrefs;
import org.cycads.parser.association.RecordFileReader;
import org.cycads.ui.progress.Progress;

public class Loaders
{
	public static void loadDbxrefTargetAnnotation(
			RecordFileReader< ? extends AnnotationRecord< ? extends DbxrefAnnotable, ? extends Dbxref>> reader,
			AnnotationMethod method, Progress progress, Progress progressError) throws IOException {

		AnnotationRecord< ? extends DbxrefAnnotable, ? extends Dbxref> record;

		while ((record = getNextValidRecord(reader, progressError)) != null) {
			DbxrefAnnotation< ? , ? , ? , ? > annot = record.getSource().addDbxrefAnnotation(method, record.getTarget());
			String score = record.getScore();
			if (annot != null) {
				if (score != null) {
					annot.setScore(score);
				}
				progress.completeStep();
			}
			else {
				progressError.completeStep();
			}
		}
	}

	public static void loadDbxrefsTargetAnnotation(
			RecordFileReader< ? extends AnnotationRecord< ? extends DbxrefAnnotable, ? extends Dbxrefs>> reader,
			AnnotationMethod method, Progress progress, Progress progressError) throws IOException {

		AnnotationRecord< ? extends DbxrefAnnotable, ? extends Dbxrefs> record;

		while ((record = getNextValidRecord(reader, progressError)) != null) {
			List<Dbxref> dbxrefs = record.getTarget().getListDbxref();
			for (Dbxref dbxref : dbxrefs) {
				DbxrefAnnotation< ? , ? , ? , ? > annot = record.getSource().addDbxrefAnnotation(method, dbxref);
				String score = record.getScore();
				if (annot != null) {
					if (score != null) {
						annot.setScore(score);
					}
					progress.completeStep();
				}
				else {
					progressError.completeStep();
				}
			}
		}
	}

	public static void loadDbxrefsDbxrefsAnnotation(
			RecordFileReader< ? extends AnnotationRecord< ? extends Dbxrefs, ? extends Dbxrefs>> reader,
			AnnotationMethod method, Progress progress, Progress progressError) throws IOException {

		AnnotationRecord< ? extends Dbxrefs, ? extends Dbxrefs> record;

		while ((record = getNextValidRecord(reader, progressError)) != null) {
			List<Dbxref> dbxrefsSource = record.getSource().getListDbxref();
			for (Dbxref dbxrefSource : dbxrefsSource) {
				List<Dbxref> dbxrefsTarget = record.getTarget().getListDbxref();
				for (Dbxref dbxrefTarget : dbxrefsTarget) {
					DbxrefAnnotation< ? , ? , ? , ? > annot = dbxrefSource.addDbxrefAnnotation(method, dbxrefTarget);
					String score = record.getScore();
					if (annot != null) {
						if (score != null) {
							annot.setScore(score);
						}
						progress.completeStep();
					}
					else {
						progressError.completeStep();
					}
				}
			}
		}
	}

	public static <R extends AssociationRecord< ? , ? >> R getNextValidRecord(RecordFileReader<R> reader,
			Progress progressError) throws IOException {
		R record = null;
		boolean read = false;
		while (!read) {
			try {
				record = reader.read();
				read = true;
			}
			catch (FileParserError e) {
				if (ParametersDefault.isDebugging()) {
					e.printStackTrace();
				}
				progressError.completeStep();
				//create fakeannotation????
			}
		}
		return record;
	}

}
