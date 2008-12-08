/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

public interface Organism<SEQ extends Sequence< ? , ? , ? , ? , ? , ? >>
{

	public Collection<SEQ> getSequences();

	public Collection<SEQ> getSequences(double version);

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