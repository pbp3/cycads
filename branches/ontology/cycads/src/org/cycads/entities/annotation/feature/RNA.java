/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.sequence.Sequence;

public interface RNA<RNA_TYPE extends RNA< ? , ? , ? , ? , ? , ? >, L extends Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? >, SEQ extends Sequence< ? , ? , ? , ? , ? , ? >, M extends AnnotationMethod, G extends Gene< ? , ? , ? , ? , ? >, C extends CDS< ? , ? , ? , ? , ? >>
		extends Feature<RNA_TYPE, L, SEQ, M>
{
	public Collection<G> getGenesContains();

	public G getGeneParent();

	public void setGeneParent(G gene);

	public Collection<C> getCDSProducts();

	public boolean isMRNA();

}
