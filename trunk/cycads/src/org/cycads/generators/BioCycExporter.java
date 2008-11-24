/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import org.cycads.entities.biojava.Organism;

public interface BioCycExporter
{

	public void export(Organism org, int version, FeatureFilter featureFilter, BioCycStream stream);

}