/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import org.cycads.entities.biojava1.FeatureFilter;
import org.cycads.entities.biojava1.Organism;

public interface BioCycExporter
{

	public void export(Organism org, int version, FeatureFilter featureFilter, BioCycStream stream);

}