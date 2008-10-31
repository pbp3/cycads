/*
 * Created on 20/10/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface Organism
{
	public String getName();

	public int getNumberID();

	public Collection<Sequence> getSequences(int version);

}
