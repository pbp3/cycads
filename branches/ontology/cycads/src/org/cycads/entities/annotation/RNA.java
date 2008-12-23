/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.sequence.Subsequence;

public interface RNA<RNA_TYPE extends RNA< ? , ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? >, M extends AnnotationMethod, G extends Gene< ? , ? , ? , ? >, C extends CDS< ? , ? , ? , ? >>
		extends AnnotFeature<RNA_TYPE, SS, M>
{
	public Collection<G> getGenesContains();

	public G getGeneParent();

	public void setGeneParent(G gene);

	public Collection<C> getCDSProducts();

	public boolean isMRNA();

}
