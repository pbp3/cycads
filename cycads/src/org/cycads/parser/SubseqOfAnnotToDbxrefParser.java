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
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.association.LineRecordFileReader;
import org.cycads.ui.progress.Progress;

public class SubseqOfAnnotToDbxrefParser
{

	private static final String		DBNAME_GENERIC	= "*";
	private final EntityFactory		factory;
	private final Progress			progress;
	private final Progress			progressError;
	private AnnotationMethod		method;
	private final Organism			organism;
	private final String			annotDBName;
	private final int				dbxrefColumnIndex;
	private final String			dbxrefDBName;
	private int						scoreColumnIndex;
	private final int				annotColumnIndex;
	private boolean					isSynonym;
	private final Collection<Type>	annotTypesFake;

	public SubseqOfAnnotToDbxrefParser(EntityFactory factory, Progress progress, AnnotationMethod method,
			Organism organism, int annotColumnIndex, String annotDBName, int dbxrefColumnIndex, String dbxrefDBName,
			int scoreColumnIndex, boolean createFake, Progress progressError) {
		this(factory, progress, organism, annotColumnIndex, annotDBName, dbxrefColumnIndex, dbxrefDBName, createFake,
			progressError);
		this.method = method;
		this.scoreColumnIndex = scoreColumnIndex;
		this.isSynonym = false;
	}

	public SubseqOfAnnotToDbxrefParser(EntityFactory factory, Progress progress, Organism organism,
			int annotColumnIndex, String annotDBName, int dbxrefColumnIndex, String dbxrefDBName, boolean createFake,
			Progress progressError) {
		this.factory = factory;
		this.progress = progress;
		this.progressError = progressError;
		this.method = null;
		this.organism = organism;
		this.annotColumnIndex = annotColumnIndex;
		this.annotDBName = annotDBName;
		this.dbxrefColumnIndex = dbxrefColumnIndex;
		this.dbxrefDBName = dbxrefDBName;
		this.scoreColumnIndex = -1;
		this.isSynonym = true;
		this.annotTypesFake = new ArrayList<Type>(1);
		this.annotTypesFake.add(factory.getType(ParametersDefault.getAnnotationFakeType()));
	}

	public void parse(File f) throws IOException {
		parse(new BufferedReader(new FileReader(f)));
	}

	public void parse(String fileName) throws IOException {
		parse(new BufferedReader(new FileReader(fileName)));
	}

	public void parse(BufferedReader br) throws IOException {
		if ()
		LineRecordFileReader fileReader = new LineRecordFileReader<R>();
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.length() > 0 && !line.startsWith(ParametersDefault.annotToDbxrefFileComment())) {
				String[] sep = line.split(ParametersDefault.annotToDbxrefFileSeparator());
				if (sep.length > annotColumnIndex) {
					String[] annotSynStrs = cleanTextDelimiter(sep[annotColumnIndex]).trim().split(
						ParametersDefault.annotToDbxrefFileAnnotsSeparator());
					for (String annotSynStr : annotSynStrs) {
						annotSynStr = annotSynStr.trim();
						if (annotSynStr.length() > 0) {
							Dbxref annotSynonym = factory.getDbxref(annotDBName, annotSynStr);
							Collection<Annotation> annots = organism.getAnnotations(null, null, annotSynonym);
							if (annots == null || annots.isEmpty()) {
								SubseqAnnotation annot = createFakeAnnot(annotSynonym);
								if (annots == null) {
									annots = new ArrayList<SubseqAnnotation>(1);
								}
								annots.add(annot);
							}
							if (sep.length > dbxrefColumnIndex) {
								String score = null;
								if (!isSynonym && sep.length > scoreColumnIndex && scoreColumnIndex >= 0) {
									score = cleanTextDelimiter(sep[scoreColumnIndex]);
								}
								addDbxref(annots, cleanTextDelimiter(sep[dbxrefColumnIndex]).trim(), score);
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

	private void addDbxref(Collection<Annotation> annots, String dbxrefsNames, String score) {
		String[] dbxrefNames = dbxrefsNames.split(ParametersDefault.annotToDbxrefFileDbxrefsSeparator());
		for (String dbxrefName : dbxrefNames) {
			Dbxref dbxref;
			dbxrefName = dbxrefName.trim();
			if (dbxrefDBName.equals(DBNAME_GENERIC)) {
				String[] syns1 = dbxrefName.split(ParametersDefault.getDbxrefToStringSeparator());
				dbxref = factory.getDbxref(syns1[0].trim(), syns1[1].trim());
			}
			else {
				dbxref = factory.getDbxref(dbxrefDBName, dbxrefName);
			}
			progress.completeStep();
			for (Annotation<Subsequence, ? > annot : annots) {
				if (!isSynonym) {
					Annotation ret = ((Subsequence) annot.getSource()).addDbxrefAnnotation(method, dbxref);
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

	private Annotation createFakeAnnot(Dbxref annotSynonym) {
		System.out.println("Annotation not found: " + annotSynonym.toString());
		progressError.completeStep();
		Sequence seq = organism.createNewSequence(ParametersDefault.getSeqVersionFake());
		Subsequence subseq = seq.createSubsequence(ParametersDefault.getSubseqStartFake(),
			ParametersDefault.getSubseqEndFake(), null);
		AnnotationMethod methodFake = factory.getAnnotationMethod(ParametersDefault.getAnnotationMethodFake());
		Annotation annot = subseq.addAnnotation(factory.getFeature(ParametersDefault.getAnnotationFakeFeature()),
			methodFake, null, annotTypesFake);
		annot.addSynonym(annotSynonym);
		return annot;
	}

}
