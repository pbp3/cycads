/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.dBLink.DBLinkAnnotation;
import org.cycads.entities.annotation.dBLink.DBLinkAnnotationContainer;
import org.cycads.entities.annotation.dBLink.DBLinkAnnotationSource;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.feature.CDS;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureCollection;
import org.cycads.entities.annotation.feature.FeatureSource;
import org.cycads.entities.annotation.feature.Gene;
import org.cycads.entities.annotation.feature.RNA;

public interface Location<D extends DBLinkAnnotation<D, S, R, M>, S extends Location< ? , ? , ? , ? , ? , ? , ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod, SEQ extends Sequence< ? , ? , ? , ? , ? , ? >, F extends Feature< ? , ? , ? , ? >, C extends CDS< ? , ? , ? , ? , ? >, RN extends RNA< ? , ? , ? , ? , ? , ? >, G extends Gene< ? , ? , ? , ? , ? >>
		extends FeatureSource<F, C, RN, G, M>, FeatureCollection<F, S, M, C, RN, G>, DBLinkAnnotationSource<D, R, M>,
		DBLinkAnnotationContainer<D, S, R, M>
{

	public Collection<Intron> getIntrons();

	public boolean addIntron(Intron intron);

	public Intron addIntron(int startPos, int endPos);

	public boolean isPositiveStrand();

	public int getEnd();

	public int getStart();

	public int getMinPosition();

	public int getMaxPosition();

	public SEQ getSequence();

}