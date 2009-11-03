package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.EntityFinder;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.factory.EntityFeatureFactory;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;
import org.cycads.parser.association.Tools;

public class SubseqsFactory implements ObjectFactory<Collection<Subsequence>>
{
	ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;
	EntityFinder						entityFinder;

	private final boolean				createFake;
	private Organism					organism;
	private EntityMethodFactory			methodFactory;
	private EntityFeatureFactory		featureFactory;
	private EntityTypeFactory			typeFactory;

	public SubseqsFactory(ObjectFactory<Collection<Dbxref>> dbxrefsFactory, EntityFinder entityFinder) {
		this.dbxrefsFactory = dbxrefsFactory;
		this.entityFinder = entityFinder;
		this.createFake = false;
	}

	public SubseqsFactory(ObjectFactory<Collection<Dbxref>> dbxrefsFactory, EntityFinder entityFinder,
			Organism organism, EntityMethodFactory methodFactory, EntityFeatureFactory featureFactory,
			EntityTypeFactory typeFactory) {
		this.dbxrefsFactory = dbxrefsFactory;
		this.entityFinder = entityFinder;
		this.organism = organism;
		this.methodFactory = methodFactory;
		this.featureFactory = featureFactory;
		this.typeFactory = typeFactory;
		this.createFake = true;
	}

	@Override
	public Collection<Subsequence> create(String[] values) throws ParserException {
		Collection<Dbxref> dbxrefs = dbxrefsFactory.create(values);
		Collection<Subsequence> ret = new ArrayList<Subsequence>(1);
		for (Dbxref dbxref : dbxrefs) {
			Collection objs = entityFinder.getEntitiesBySynonym(dbxref, null);
			if ((objs == null || objs.isEmpty()) && createFake) {
				ret.add(Tools.createFakeSubseqAnnot(dbxref, organism, methodFactory, featureFactory, typeFactory).getSource());
			}
			else {
				for (Object obj : objs) {
					if (obj instanceof Association) {
						Association assoc = (Association) obj;
						if (assoc.getSource() instanceof Subsequence) {
							ret.add((Subsequence) assoc.getSource());
						}
					}
					else {
						if (obj instanceof Subsequence) {
							ret.add((Subsequence) obj);
						}
					}
				}
			}
		}
		return ret;
	}
}
