/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.ExternalAnnotationSource;
import org.cycads.entities.annotation.Feature;
import org.cycads.entities.note.NoteHolder;

public interface Sequence extends NoteHolder<Sequence>, ExternalAnnotationSource
{

	public Collection<Feature> getFeatures();

	public Collection<Feature> getFeatures(FeatureFilter featureFilter);

	public String getDescription();

	public double getVersion();

	public String getName();

	public Organism getOrganism();

}