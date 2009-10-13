/*
 * Created on 29/09/2009
 */
package org.cycads.ui.loader;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.cycads.entities.annotation.DbxrefAnnotable;
import org.cycads.entities.annotation.DbxrefAnnotation;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.FileParserException;
import org.cycads.parser.association.AnnotationRecord;
import org.cycads.parser.association.AssociationRecord;
import org.cycads.parser.association.DbxrefsField;
import org.cycads.parser.association.RecordFileReader;
import org.cycads.ui.progress.Progress;

public class Loaders
{
	public static void loadDbxrefTargetAnnotation(
			RecordFileReader< ? extends AnnotationRecord< ? extends DbxrefAnnotable, ? extends Dbxref>> reader,
			Progress progress, Progress progressError) throws IOException {

		AnnotationRecord< ? extends DbxrefAnnotable, ? extends Dbxref> record;

		while ((record = getNextValidRecord(reader, progressError)) != null) {
			DbxrefAnnotation< ? , ? , ? , ? > annot = record.getSource().addDbxrefAnnotation(record.getMethod(),
				record.getTarget());
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
			RecordFileReader< ? extends AnnotationRecord< ? extends DbxrefAnnotable, ? extends DbxrefsField>> reader,
			Progress progress, Progress progressError) throws IOException {

		AnnotationRecord< ? extends DbxrefAnnotable, ? extends DbxrefsField> record;

		while ((record = getNextValidRecord(reader, progressError)) != null) {
			List<Dbxref> dbxrefs = record.getTarget().getListDbxref();
			for (Dbxref dbxref : dbxrefs) {
				DbxrefAnnotation< ? , ? , ? , ? > annot = record.getSource().addDbxrefAnnotation(record.getMethod(),
					dbxref);
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

	public static void loadDbxrefAnnotation(
			RecordFileReader< ? extends AnnotationRecord< ? extends Collection< ? extends DbxrefAnnotable>, ? extends Collection< ? extends Dbxref>>> reader,
			Progress progress, Progress progressError) throws IOException {

		AnnotationRecord< ? extends Collection< ? extends DbxrefAnnotable>, ? extends Collection< ? extends Dbxref>> record;

		while ((record = getNextValidRecord(reader, progressError)) != null) {
			Collection< ? extends DbxrefAnnotable> annotsSource = record.getSource();
			for (DbxrefAnnotable annotSource : annotsSource) {
				Collection< ? extends Dbxref> dbxrefsTarget = record.getTarget();
				for (Dbxref dbxrefTarget : dbxrefsTarget) {
					DbxrefAnnotation< ? , ? , ? , ? > annot = annotSource.addDbxrefAnnotation(record.getMethod(),
						dbxrefTarget);
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
			catch (FileParserException e) {
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
