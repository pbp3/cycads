/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.DBLinkSource;
import org.cycads.entities.annotation.FeatureSource;

public interface Location extends FeatureSource, DBLinkSource
{

	public Collection<Intron> getIntrons();

	public void addIntron(Intron intron);

	public Intron addIntron(int startPos, int endPos);

	public boolean isPositiveStrand();

	/**
	 * Getter of the property <tt>end</tt>
	 * 
	 * @return Returns the end.
	 * 
	 */
	public int getEnd();

	/*
	 * (non-javadoc)
	 */
	/**
	 * Getter of the property <tt>start</tt>
	 * 
	 * @return Returns the start.
	 * 
	 */
	public int getStart();

	public Sequence getSequence();

}