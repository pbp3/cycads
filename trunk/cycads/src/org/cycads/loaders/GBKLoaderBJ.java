/*
 * Created on 20/10/2008
 */
package org.cycads.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;

import org.biojava.bio.BioException;
import org.biojava.ontology.InvalidTermException;
import org.biojavax.Note;
import org.biojavax.RichObjectFactory;
import org.biojavax.SimpleRichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.RichSequenceIterator;
import org.biojavax.bio.seq.SimpleRichFeatureRelationship;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.CDS;
import org.cycads.entities.CDSBJ;
import org.cycads.entities.Method;
import org.cycads.entities.MethodTypeBJ;
import org.cycads.general.CacheCleaner;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.TermsAndOntologies;
import org.cycads.ui.progress.Progress;

public class GBKLoaderBJ extends FileLoaderAbstract
{
	public GBKLoaderBJ(Progress progress, CacheCleaner cacheCleaner) {
		super(progress, cacheCleaner);
	}

	public void load(BufferedReader br) throws IOException {
		progress.init();

		Pattern ecPattern = Pattern.compile(ParametersDefault.gbkECTagExpression());
		Pattern genePattern = Pattern.compile(ParametersDefault.gbkGeneTagExpression());
		Pattern rnaPattern = Pattern.compile(ParametersDefault.gbkRNATagExpression());
		Pattern cdsPattern = Pattern.compile(ParametersDefault.gbkCDSTagExpression());

		ComparableTerm ecTerm = TermsAndOntologies.getTermTypeEC();
		ComparableTerm geneTerm = TermsAndOntologies.getTermTypeGene();
		ComparableTerm cdsTerm = TermsAndOntologies.getTermTypeCDS();

		Method methodCDSToEC = MethodTypeBJ.CDS_TO_EC.getOrCreateMethod(ParametersDefault.gBKLoaderMethodCDSToECName());

		// we are reading DNA sequences
		RichSequenceIterator seqs = RichSequence.IOTools.readGenbankDNA(br, RichObjectFactory.getDefaultNamespace());
		while (seqs.hasNext()) {
			RichSequence seq;
			try {
				seq = seqs.nextRichSequence();
			}
			catch (BioException e) {
				e.printStackTrace();
				throw new IOException(e.getMessage());
			}
			Set<RichFeature> features = seq.getFeatureSet();
			ArrayList<RichFeature> genes = new ArrayList<RichFeature>();
			ArrayList<RichFeature> rNAs = new ArrayList<RichFeature>();
			ArrayList<RichFeature> cDSs = new ArrayList<RichFeature>();
			for (RichFeature feature : features) {
				try {
					if (genePattern.matcher(feature.getType()).matches()) {
						genes.add(feature);
						feature.setTypeTerm(geneTerm);
					}
					else if (rnaPattern.matcher(feature.getType()).matches()) {
						rNAs.add(feature);
					}
					else if (cdsPattern.matcher(feature.getType()).matches()) {
						feature.setTypeTerm(cdsTerm);
						cDSs.add(feature);
						SimpleRichAnnotation annot = (SimpleRichAnnotation) feature.getAnnotation();
						Set<Note> notes = annot.getNoteSet();
						for (Note note : notes) {
							if (ecPattern.matcher(note.getTerm().getName()).matches()) {
								note.setTerm(ecTerm);
								CDS cds = new CDSBJ(feature);
								cds.addAnnotation(methodCDSToEC, note.getValue());
							}
						}
					}
				}
				catch (InvalidTermException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			for (RichFeature gene : genes) {
				for (RichFeature rNA : rNAs) {
					for (RichFeature cDS : cDSs) {
						if (rNA.getLocation().contains(cDS.getLocation())) {
							rNA.addFeatureRelationship(new SimpleRichFeatureRelationship(rNA, cDS,
								SimpleRichFeatureRelationship.getContainsTerm(), 0));
						}
					}
					if (gene.getLocation().contains(rNA.getLocation())) {
						gene.addFeatureRelationship(new SimpleRichFeatureRelationship(gene, rNA,
							SimpleRichFeatureRelationship.getContainsTerm(), 0));
					}
				}
			}
			BioJavaxSession.session.saveOrUpdate("Sequence", seq);
			progress.completeStep();
			cacheCleaner.clear();
		}
		Object[] a1 = {progress.getStep()};
		progress.finish(a1);
	}
}
