/*
 * Created on 25/09/2009
 * Get annotations using synonyms. Create only the objects already persisted
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.factory.EntityFeatureFactory;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;
import org.cycads.parser.association.Tools;

public class ExistedAnnotationsFactoryBySynonym implements ObjectFactory<Collection<Annotation>>
{
	private AnnotationFinder					annotationFinder;
	private ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;
	boolean										createFake;
	//the attributes below will be used in the fake creation
	private Organism							organism;
	private EntityMethodFactory					methodFactory;
	private EntityFeatureFactory				featureFactory;
	private EntityTypeFactory					typeFactory;

	public ExistedAnnotationsFactoryBySynonym(AnnotationFinder annotationFinder,
			ObjectFactory<Collection<Dbxref>> dbxrefsFactory) {
		this.annotationFinder = annotationFinder;
		this.dbxrefsFactory = dbxrefsFactory;
		this.createFake = false;
	}

	public ExistedAnnotationsFactoryBySynonym(AnnotationFinder annotationFinder,
			ObjectFactory<Collection<Dbxref>> dbxrefsFactory, Organism organism, EntityMethodFactory methodFactory,
			EntityFeatureFactory featureFactory, EntityTypeFactory typeFactory) {
		this.annotationFinder = annotationFinder;
		this.dbxrefsFactory = dbxrefsFactory;
		this.createFake = true;
		this.organism = organism;
		this.methodFactory = methodFactory;
		this.featureFactory = featureFactory;
		this.typeFactory = typeFactory;
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
				annots.add(Tools.createFakeSubseqAnnot(dbxref, organism, methodFactory, featureFactory, typeFactory));
			}
			if (annots != null && !annots.isEmpty()) {
				ret.addAll(annots);
			}
		}
		return ret;
	}

}
