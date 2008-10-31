/*
 * Created on 31/10/2008
 */
package org.cycads.entities;

import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;

public class SequenceFeatureBJ implements SequenceFeature
{
	RichFeature	feature;

	public SequenceFeatureBJ(RichFeature feature) {
		this.feature = feature;
	}

	public Collection<DBRef> getDBRefs() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<SequenceFeature> getFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	public Location getLocation() {
		return new LocationBJ(feature.getLocation());
	}

	public Collection<Note> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return feature.getName();
	}

	public Sequence getSequence() {
		return new SequenceBJ(feature.getSequence());
	}

	public String getType() {
		return feature.getType();
	}

}
