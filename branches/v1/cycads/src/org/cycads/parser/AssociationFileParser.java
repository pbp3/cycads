/*
 * Created on 16/03/2009
 */
package org.cycads.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;

public abstract class AssociationFileParser
{

	private static final String	DBNAME_GENERIC	= "*";
	private String				fileComment;
	private String				fileSeparator;
	private String				dbxrefInstancesSeparator;
	private String				dbxref1Delimiter;
	private String				dbxref2Separator;
	private String				dbxref2Delimiter;

	public AssociationFileParser() {
	}

	public void parse(File f, int accession1ColumnIndex, String dBName1, int accession2ColumnIndex, String dBName2,
			EntityFactory factory) throws IOException {
		parse(new BufferedReader(new FileReader(f)), accession1ColumnIndex, dBName1, accession2ColumnIndex, dBName2,
			factory);
	}

	public void parse(String fileName, int accession1ColumnIndex, String dBName1, int accession2ColumnIndex,
			String dBName2, EntityFactory factory) throws IOException {
		parse(new BufferedReader(new FileReader(fileName)), accession1ColumnIndex, dBName1, accession2ColumnIndex,
			dBName2, factory);
	}

	public void parse(BufferedReader br, int accession1ColumnIndex, String dBName1, int accession2ColumnIndex,
			String dBName2, EntityFactory factory) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.length() > 0 && !line.startsWith(ParametersDefault.associationFileComment())) {
				String[] sep = line.split(ParametersDefault.associationFileSeparator());
				if (sep.length > accession2ColumnIndex) {
					String[] annotSynStrs = cleanTextDelimiter(sep[accession2ColumnIndex]).trim().split(
						ParametersDefault.annotToDbxrefFileAnnotsSeparator());
					for (String annotSynStr : annotSynStrs) {
						annotSynStr = annotSynStr.trim();
						if (annotSynStr.length() > 0) {
							Dbxref annotSynonym = factory.getDbxref(dBName1, annotSynStr);
							Collection<SubseqAnnotation> annots = organism.getAnnotations(null, null, annotSynonym);
							if (annots == null || annots.isEmpty()) {
								SubseqAnnotation annot = createFakeAnnot(annotSynonym);
								if (annots == null) {
									annots = new ArrayList<SubseqAnnotation>(1);
								}
								annots.add(annot);
							}
							if (sep.length > accession1ColumnIndex) {
								String score = null;
								if (!isSynonym && sep.length > scoreColumnIndex && scoreColumnIndex >= 0) {
									score = cleanTextDelimiter(sep[scoreColumnIndex]);
								}
								addDbxref(annots, cleanTextDelimiter(sep[accession1ColumnIndex]).trim(), score);
							}
						}
					}
				}
			}
		}
	}

	private String cleanTextDelimiter(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}
		int start = 0, end = text.length();
		if (text.startsWith(ParametersDefault.annotToDbxrefFileTextDelimiter())) {
			start = 1;
		}
		if (text.endsWith(ParametersDefault.annotToDbxrefFileTextDelimiter())) {
			end = end - 1;
		}
		return text.substring(start, end);
	}

	private void addDbxref(Collection<SubseqAnnotation> annots, String dbxrefsNames, String score) {
		String[] dbxrefNames = dbxrefsNames.split(ParametersDefault.annotToDbxrefFileDbxrefsSeparator());
		for (String dbxrefName : dbxrefNames) {
			Dbxref dbxref;
			dbxrefName = dbxrefName.trim();
			if (dBName2.equals(DBNAME_GENERIC)) {
				String[] syns1 = dbxrefName.split(ParametersDefault.getDbxrefToStringSeparator());
				dbxref = factory.getDbxref(syns1[0].trim(), syns1[1].trim());
			}
			else {
				dbxref = factory.getDbxref(dBName2, dbxrefName);
			}
			progress.completeStep();
			for (SubseqAnnotation annot : annots) {
				if (!isSynonym) {
					Annotation ret = annot.getSubsequence().addDbxrefAnnotation(method, dbxref);
					if (ret != null && score != null) {
						ret.setScore(score);
					}
				}
				else {
					annot.addSynonym(dbxref);
				}
			}
		}
	}

	private SubseqAnnotation createFakeAnnot(Dbxref annotSynonym) {
		System.out.println("Annotation not found: " + annotSynonym.toString());
		progressError.completeStep();
		Sequence seq = organism.createNewSequence(ParametersDefault.getSeqVersionFake());
		Subsequence subseq = seq.createSubsequence(ParametersDefault.getSubseqStartFake(),
			ParametersDefault.getSubseqEndFake(), null);
		AnnotationMethod methodFake = factory.getAnnotationMethod(ParametersDefault.getAnnotationMethodFake());
		SubseqAnnotation annot = subseq.addAnnotation(
			factory.getAnnotationType(ParametersDefault.getAnnotationFakeType()), methodFake);
		annot.addSynonym(annotSynonym);
		return annot;
	}

}
