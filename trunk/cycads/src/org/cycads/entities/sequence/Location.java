/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.dBLink.DBLinkContainer;
import org.cycads.entities.annotation.dBLink.DBLinkSource;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.feature.FeatureCollection;
import org.cycads.entities.annotation.feature.FeatureSource;

public interface Location<D extends DBLink<S, R>, S extends Location< ? , ? , ? , ? >, R extends DBRecord< ? , ? , ? >, SEQ extends Sequence< ? , ? , ? >>
		extends FeatureSource, FeatureCollection, DBLinkSource<D, S, R>, DBLinkContainer<D, S, R>
{

	public Collection<Intron> getIntrons();

	public boolean addIntron(Intron intron);

	public Intron addIntron(int startPos, int endPos);

	public boolean isPositiveStrand();

	public int getEnd();

	public int getStart();

	public SEQ getSequence();

}