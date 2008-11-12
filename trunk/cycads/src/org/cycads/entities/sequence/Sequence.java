/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.note.NoteHolder;
import org.cycads.entities.sequence.feature.Feature;
import org.cycads.entities.sequence.feature.FeatureFilter;

public interface Sequence extends NoteHolder<Sequence>
{

	public Collection<SequenceToDBAnnotation> getDBLinks();

	public Collection<Feature> getFeatures();

	public Collection<Feature> getFeatures(FeatureFilter featureFilter);

	public String getDescription();

	public double getVersion();

	public String getName();

	public Organism getOrganism();

}