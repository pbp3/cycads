package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.parser.ParserException;

public class SubseqsFactory implements ObjectFactory<Collection<Subsequence>>
{
	ExistedEntitiesFactoryBySynonym	existedEntitiesFactoryBySynonym;

	//	ObjectFactory<Collection<Dbxref>>	dbxrefsFactory;
	//	EntityFinder						entityFinder;
	//
	//	private final boolean				createFake;
	//	private Organism					organism;
	//	private EntityMethodFactory			methodFactory;
	//	private EntityFeatureFactory		featureFactory;
	//	private EntityTypeFactory			typeFactory;

	public SubseqsFactory(ExistedEntitiesFactoryBySynonym existedEntitiesFactoryBySynonym) {
		this.existedEntitiesFactoryBySynonym = existedEntitiesFactoryBySynonym;
	}

	//	public SubseqsFactory(ObjectFactory<Collection<Dbxref>> dbxrefsFactory, EntityFinder entityFinder) {
	//		this.dbxrefsFactory = dbxrefsFactory;
	//		this.entityFinder = entityFinder;
	//		this.createFake = false;
	//	}
	//
	//	public SubseqsFactory(ObjectFactory<Collection<Dbxref>> dbxrefsFactory, EntityFinder entityFinder,
	//			Organism organism, EntityMethodFactory methodFactory, EntityFeatureFactory featureFactory,
	//			EntityTypeFactory typeFactory) {
	//		this.dbxrefsFactory = dbxrefsFactory;
	//		this.entityFinder = entityFinder;
	//		this.organism = organism;
	//		this.methodFactory = methodFactory;
	//		this.featureFactory = featureFactory;
	//		this.typeFactory = typeFactory;
	//		this.createFake = true;
	//	}
	//

	@Override
	public Collection<Subsequence> create(String[] values) throws ParserException {
		Collection<Subsequence> ret = new ArrayList<Subsequence>(1);
		Collection<BasicEntity> objs = existedEntitiesFactoryBySynonym.create(values);
		if (objs != null && !objs.isEmpty()) {
			for (BasicEntity obj : objs) {
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
		return ret;
	}
}
