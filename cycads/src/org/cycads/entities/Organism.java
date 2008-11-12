/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface Organism
{

	public Collection<Sequence> getSequences();

	public Collection<Sequence> getSequences(double version);

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * 
	 */
	public String getName();

	/**
	 * Getter of the property <tt>id</tt>
	 * 
	 * @return Returns the id.
	 * 
	 */
	public int getId();

}