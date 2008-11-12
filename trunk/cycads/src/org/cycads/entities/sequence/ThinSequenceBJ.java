/*
 * Created on 29/10/2008
 */
package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.biojava.FeatureFilterByType;
import org.cycads.entities.sequence.feature.Feature;
import org.cycads.entities.sequence.feature.FeatureBJ;
import org.cycads.entities.sequence.feature.FeatureFilter;
import org.cycads.general.biojava.BioJavaxSession;
import org.hibernate.Query;

public class ThinSequenceBJ implements Sequence
{
	int	id;

	public ThinSequenceBJ(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Collection<Feature> getFeatures(FeatureFilter featureFilter) {
		if (featureFilter instanceof FeatureFilterByType) {

			Query query = BioJavaxSession.createQuery("select f.id from Feature as f join f.parent as b where "
				+ "b.id=:seqId ");
			query.setInteger("seqId", id);
			Collection<Integer> results = query.list();
			Collection<Feature> ret = new ArrayList<Feature>();
			for (Integer featureId : results) {
				Feature f = new FeatureBJ(featureId);
				if (featureFilter.accept(f)) {
					ret.add(f);
				}
			}
			return ret;
		}
		else {
			return null;
		}
	}

	public Collection<SequenceToDBAnnotation> getDBLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Feature> getFeatures() {
		Query query = BioJavaxSession.createQuery("select f.id from Feature as f join f.parent as b where "
			+ "b.id=:seqId ");
		query.setInteger("seqId", id);
		Collection<Integer> results = query.list();
		Collection<Feature> ret = new ArrayList<Feature>();
		for (Integer featureId : results) {

			ret.add(new FeatureBJ(featureId));
		}
		return ret;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<SequenceNote> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Organism getOrganism() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

}
