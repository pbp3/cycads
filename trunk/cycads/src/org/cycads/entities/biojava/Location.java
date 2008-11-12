/*
 * Created on 31/10/2008
 */
package org.cycads.entities.biojava;

import java.util.Collection;

import org.cycads.generators.Intron;

public interface Location
{
	public int getStart();

	public int getEnd();

	public Collection<Intron> getIntrons();

	public void shift(int qtty);

	public boolean isPositiveStrand();
}
