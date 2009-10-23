/*
 * Created on 23/10/2009
 */
package org.cycads.parser.association.factory.independent;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.EntityFinder;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;

public class SubsequenceFactoryByAnnotationSynonym<E extends Subsequence>
		implements IndependentObjectFactory<Collection<E>>
{
	IndependentDbxrefFactory<Dbxref>	dbxrefFactory;
	EntityFinder<Annotation>			finder;
	String								objectType	= Annotation.ENTITY_TYPE_NAME;

	public SubsequenceFactoryByAnnotationSynonym(IndependentDbxrefFactory<Dbxref> dbxrefFactory,
			EntityFinder<Annotation> finder) {
		this.dbxrefFactory = dbxrefFactory;
		this.finder = finder;
	}

	@Override
	public Collection<E> create(String value) throws ParserException {
		Collection< ? extends Annotation> annots = finder.getEntitiesBySynonym(dbxrefFactory.create(value), objectType);
		Collection<E> ret = new ArrayList<E>(annots.size());
		for (Annotation annot : annots) {
			if (annot.getSource() instanceof Subsequence) {
				ret.add((E) annot.getSource());
			}
		}
		return ret;
	}
}
