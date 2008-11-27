/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.DBLinkCollection;
import org.cycads.entities.annotation.DBLinkSource;
import org.cycads.entities.annotation.FeatureCollection;
import org.cycads.entities.annotation.FeatureSource;

public interface Location extends FeatureSource, FeatureCollection, DBLinkSource, DBLinkCollection
{

	public Collection<Intron> getIntrons();

	public Intron addIntron(Intron intron);

	public Intron addIntron(int startPos, int endPos);

	public boolean isPositiveStrand();

	public int getEnd();

	public int getStart();

	public Sequence getSequence();

}