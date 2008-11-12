/*
 * Created on 11/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

public interface ILocation
{

	public Collection<IIntron> getIntrons();

	public void addIntron(IIntron intron);

	public IIntron addIntron(int startPos, int endPos);

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