/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

public interface EntityFilter<E extends EntityObject>
{
	public boolean accept(E annotation);
}
