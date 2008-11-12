/*
 * Created on 11/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface Location
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

}