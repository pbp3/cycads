/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

public interface Intron extends Comparable<Intron>
{
	public int getStart();

	public int getEnd();

	public int getMin();

	public int getMax();

}