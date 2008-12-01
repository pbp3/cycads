/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

public interface CDS extends Feature
{
	public Collection<RNA> getRNAsContains();

	public RNA getRNASource();

	public void setRNASource(RNA rna);
}
