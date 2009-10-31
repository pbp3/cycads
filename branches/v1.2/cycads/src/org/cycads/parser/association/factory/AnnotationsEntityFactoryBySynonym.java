/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityFeatureFactory;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.ParserException;

public class AnnotationsEntityFactoryBySynonym implements ObjectFactory<Collection<Annotation>>
{
	AnnotationFinder					annotationFinder;
	ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;
	boolean								createFake;
	private Organism					organism;
	private EntityMethodFactory			methodFactory;
	private EntityFeatureFactory		featureFactory;
	private Collection<Type>			annotTypesFake;

	public AnnotationsEntityFactoryBySynonym(AnnotationFinder annotationFinder,
			ObjectFactory<Collection<Dbxref>> dbxrefsFactory) {
		this.annotationFinder = annotationFinder;
		this.dbxrefsFactory = dbxrefsFactory;
		this.createFake = false;
	}

	public AnnotationsEntityFactoryBySynonym(AnnotationFinder annotationFinder,
			ObjectFactory<Collection<Dbxref>> dbxrefsFactory, Organism organism, EntityMethodFactory methodFactory,
			EntityFeatureFactory featureFactory, EntityTypeFactory typeFactory) {
		this.annotationFinder = annotationFinder;
		this.dbxrefsFactory = dbxrefsFactory;
		this.createFake = true;
		this.annotTypesFake = new ArrayList<Type>(1);
		this.annotTypesFake.add(typeFactory.getType(ParametersDefault.getAnnotationFakeType()));
		this.organism = organism;
		this.methodFactory = methodFactory;
		this.featureFactory = featureFactory;
	}

	@Override
	public Collection<Annotation> create(String[] values) throws ParserException {
		Collection<Dbxref> dbxrefs = dbxrefsFactory.create(values);
		if (dbxrefs == null || dbxrefs.isEmpty()) {
			return new ArrayList<Annotation>();
		}
		Collection<Annotation> ret = new ArrayList<Annotation>();
		Collection<Annotation> annots;
		for (Dbxref dbxref : dbxrefs) {
			annots = annotationFinder.getAnnotationsBySynonym(dbxref);
			if ((annots == null || annots.isEmpty()) && createFake) {
				if (annots == null) {
					annots = new ArrayList<Annotation>();
				}
				annots.add(createFakeAnnot(dbxref));
			}
			if (annots != null && !annots.isEmpty()) {
				ret.addAll(annots);
			}
		}
		return ret;
	}

	private Annotation createFakeAnnot(Dbxref annotSynonym) {
		System.out.println("Annotation not found: " + annotSynonym.toString());
		Sequence seq = organism.createNewSequence(ParametersDefault.getSeqVersionFake());
		Subsequence subseq = seq.createSubsequence(ParametersDefault.getSubseqStartFake(),
			ParametersDefault.getSubseqEndFake(), null);
		AnnotationMethod methodFake = methodFactory.getAnnotationMethod(ParametersDefault.getAnnotationMethodFake());
		Annotation annot = subseq.addAnnotation(
			featureFactory.getFeature(ParametersDefault.getAnnotationFakeFeature()), methodFake, null, annotTypesFake);
		annot.addSynonym(annotSynonym);
		return annot;
	}

}
