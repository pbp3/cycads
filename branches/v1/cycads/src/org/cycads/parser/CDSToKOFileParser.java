/*
 * Created on 16/03/2009
 */
package org.cycads.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.cycads.entities.EntityFactory;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.KO;
import org.cycads.general.Config;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class CDSToKOFileParser {

	EntityFactory		factory;
	Progress			progress;
	Progress			progressError;
	AnnotationMethod	method;
	Organism			organism;
	String				cdsDBName;

	public CDSToKOFileParser(EntityFactory factory, Progress progress, AnnotationMethod method, Organism organism,
			String cdsDBName, Progress progressError) {
		this.factory = factory;
		this.progress = progress;
		this.progressError = progressError;
		this.method = method;
		this.organism = organism;
		this.cdsDBName = cdsDBName;
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
			if (line.length() > 0 && !line.startsWith(Config.cdsToKOFileComment())) {
				String[] sep = line.split(Config.cdsToKOFileSeparator());
				if (sep.length == 2) {
					addAnnotation(sep[0], sep[1]);
				}
			}
		}
	}

	private void addAnnotation(String cdsName, String koName) {
		Dbxref cdsSynonym = factory.getDbxref(cdsDBName, cdsName);
		Collection<SubseqAnnotation> annots = organism.getAnnotations(null, null, cdsSynonym);
		KO ko = factory.getKO(koName);
		if (annots != null && !annots.isEmpty()) {
			for (SubseqAnnotation annot : annots) {
				annot.getSubsequence().addDbxrefAnnotation(method, ko);
				progress.completeStep();
			}
			if (annots.size() != 1) {
				System.out.println("Many (" + annots.size() + ") CDS annotations: " + cdsName);
			}
		}
		else {
			System.out.println("CDS not found: " + cdsName);
			progressError.completeStep();
			Sequence seq = organism.createNewSequence(ParametersDefault.getCDSSeqVersionFake());
			Subsequence subseq = seq.createSubsequence(ParametersDefault.getCDSSubseqStartFake(),
				ParametersDefault.getCDSSubseqEndFake(), null);
			AnnotationMethod methodFake = factory.getAnnotationMethod(ParametersDefault.getCDSAnnotationMethodFake());
			Annotation annot = subseq.addAnnotation(factory.getAnnotationTypeCDS(), methodFake);
			annot.addSynonym(cdsSynonym);
			subseq.addDbxrefAnnotation(method, ko);
			progress.completeStep();

		}
	}

}
