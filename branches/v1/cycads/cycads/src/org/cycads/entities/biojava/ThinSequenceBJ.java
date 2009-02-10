/*
 * Created on 29/10/2008
 */
package org.cycads.entities.biojava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.biojava.ontology.Term;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.generators.FeatureFilter;
import org.cycads.generators.FeatureFilterByType;
import org.hibernate.Query;

public class ThinSequenceBJ implements Sequence
{
	int	id;

	public ThinSequenceBJ(int id) {
		this.id = id;
	}

	public Collection<SequenceFeature> getFeatures(FeatureFilter featureFilter) {
		if (featureFilter instanceof FeatureFilterByType) {

			Query query = BioJavaxSession.createQuery("select f.id,f.typeTerm from AnnotFeature as f join f.parent as b where "
				+ "b.id=:seqId ");
			query.setInteger("seqId", id);
			List<Object[]> results = query.list();
			Collection<SequenceFeature> ret = new ArrayList<SequenceFeature>();
			for (Object[] result : results) {
				if (((FeatureFilterByType) featureFilter).accept(((Term) result[1]).getName())) {
					ret.add(new SequenceFeatureBJ((Integer) result[0]));
				}
			}
			return ret;
		}
		else {
			return null;
		}
	}

}
