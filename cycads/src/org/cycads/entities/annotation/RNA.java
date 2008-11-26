/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface RNA extends Feature
{
	public Collection<Gene> getGenesContains();

	public Gene getGeneSource();

	public void setGeneSource(Gene gene);

	public Collection<CDS> getCDSProducts();

}
