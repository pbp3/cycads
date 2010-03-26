/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import org.cycads.extract.pf.CycIntron;

public interface Intron extends Comparable<Intron>, CycIntron
{
	public int getLength();

	public boolean contains(Intron intron);

	public int getStart();

	public int getEnd();

	public Intron getIntersection(int start, int end);

}