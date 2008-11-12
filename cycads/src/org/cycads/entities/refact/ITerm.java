/*
 * Created on 11/11/2008
 */
package org.cycads.entities.refact;

public interface ITerm
{

	public abstract ITypeTerm getType();

	/**
	 * Getter of the property <tt>value</tt>
	 * 
	 * @return Returns the value.
	 * 
	 */
	public abstract String getValue();

}