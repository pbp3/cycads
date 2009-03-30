/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import org.cycads.extract.cyc.CycIntron;

public interface Intron extends Comparable<Intron>, CycIntron
{
	public int getLength();

	public boolean contains(Intron intron);

	public int getStart();

	public int getEnd();

}