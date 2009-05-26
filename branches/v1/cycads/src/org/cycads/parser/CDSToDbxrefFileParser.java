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

import org.cycads.entities.EntityFactory;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Config;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class CDSToDbxrefFileParser
{

	private static final String		DBNAME_GENERIC	= "*";
	private final EntityFactory		factory;
	private final Progress			progress;
	private final Progress			progressError;
	private final AnnotationMethod	method;
	private final Organism			organism;
	private final String			cdsDBName;
	private final int				dbxrefColumnIndex;
	private final String			dbxrefDBName;
	private final int				scoreColumnIndex;
	private final int				cdsColumnIndex;
	private final boolean			isAnnotation;

	public CDSToDbxrefFileParser(EntityFactory factory, Progress progress, AnnotationMethod method, Organism organism,
			int cdsColumnIndex, String cdsDBName, int dbxrefColumnIndex, String dbxrefDBName, int scoreColumnIndex,
			Progress progressError) {
		this.factory = factory;
		this.progress = progress;
		this.progressError = progressError;
		this.method = method;
		this.organism = organism;
		this.cdsColumnIndex = cdsColumnIndex;
		this.cdsDBName = cdsDBName;
		this.dbxrefColumnIndex = dbxrefColumnIndex;
		this.dbxrefDBName = dbxrefDBName;
		this.scoreColumnIndex = scoreColumnIndex;
		this.isAnnotation = true;
	}

	public CDSToDbxrefFileParser(EntityFactory factory, Progress progress, Organism organism, int cdsColumnIndex,
			String cdsDBName, int dbxrefColumnIndex, String dbxrefDBName, Progress progressError) {
		this.factory = factory;
		this.progress = progress;
		this.progressError = progressError;
		this.method = null;
		this.organism = organism;
		this.cdsColumnIndex = cdsColumnIndex;
		this.cdsDBName = cdsDBName;
		this.dbxrefColumnIndex = dbxrefColumnIndex;
		this.dbxrefDBName = dbxrefDBName;
		this.scoreColumnIndex = -1;
		this.isAnnotation = false;
	}

	public void parse(File f) throws IOException {
		parse(new BufferedReader(new FileReader(f)));
	}

	public void parse(String fileName) throws IOException {
		parse(new BufferedReader(new FileReader(fileName)));
	}

	public void parse(BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			if (line.length() > cdsColumnIndex && !line.startsWith(Config.cdsToDbxrefFileComment())) {
				String[] sep = line.split(Config.cdsToDbxrefFileSeparator());
				if (sep.length > dbxrefColumnIndex) {
					String score = null;
					if (isAnnotation && sep.length > scoreColumnIndex && scoreColumnIndex >= 0) {
						score = sep[scoreColumnIndex];
					}
					addDbxref(cleanTextDelimiter(sep[cdsColumnIndex]).trim(),
						cleanTextDelimiter(sep[dbxrefColumnIndex]).trim(), cleanTextDelimiter(score));
				}
			}
		}
	}

	private String cleanTextDelimiter(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}
		int start = 0, end = text.length();
		if (text.startsWith(Config.cdsToDbxrefTextDelimiter())) {
			start = 1;
		}
		if (text.endsWith(Config.cdsToDbxrefTextDelimiter())) {
			end = end - 1;
		}
		return text.substring(start, end);
	}

	private void addDbxref(String annotsSynStr, String dbxrefsNames, String score) {
		String[] annotSynStrs = annotsSynStr.split(ParametersDefault.CDSToDbxrefLoaderAnnotsSeparator());
		for (String annotSynStr : annotSynStrs) {
			annotSynStr = annotSynStr.trim();
			Dbxref annotSynonym = factory.getDbxref(cdsDBName, annotSynStr);
			Collection<SubseqAnnotation> annots = organism.getAnnotations(null, null, annotSynonym);
			if (annots == null || annots.isEmpty()) {
				System.out.println("CDS not found: " + annotSynStr);
				progressError.completeStep();
				Sequence seq = organism.createNewSequence(ParametersDefault.getCDSSeqVersionFake());
				Subsequence subseq = seq.createSubsequence(ParametersDefault.getCDSSubseqStartFake(),
					ParametersDefault.getCDSSubseqEndFake(), null);
				AnnotationMethod methodFake = factory.getAnnotationMethod(ParametersDefault.getCDSAnnotationMethodFake());
				SubseqAnnotation annot = subseq.addAnnotation(factory.getAnnotationTypeCDS(), methodFake);
				annot.addSynonym(annotSynonym);
				if (annots == null) {
					annots = new ArrayList<SubseqAnnotation>(1);
				}
				annots.add(annot);
			}
			String[] dbxrefNames = dbxrefsNames.split(ParametersDefault.CDSToDbxrefLoaderDbxrefsSeparator());
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
				for (SubseqAnnotation annot : annots) {
					if (isAnnotation) {
						Annotation ret = annot.getSubsequence().addDbxrefAnnotation(method, dbxref);
						if (ret != null && score != null) {
							ret.addNote(ParametersDefault.getScoreAnnotationNoteTypeName(), score);
						}
					}
					else {
						annot.addSynonym(dbxref);
					}
				}
			}
		}
	}

}
