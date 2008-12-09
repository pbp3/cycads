/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Sequence;

public interface RNA<L extends Location< ? , ? , ? , ? , ? , ? , ? , ? , ? >, SEQ extends Sequence< ? , ? , ? , ? , ? , ? >, M extends AnnotationMethod, G extends Gene< ? , ? , ? , ? >, C extends CDS< ? , ? , ? , ? >>
		extends Feature<L, SEQ, M>
{
	public Collection<G> getGenesContains();

	public G getGeneParent();

	public void setGeneParent(G gene);

	public Collection<C> getCDSProducts();

}
