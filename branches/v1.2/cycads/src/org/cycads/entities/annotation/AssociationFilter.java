/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

public interface AssociationFilter
{
	public boolean accept(Association< ? , ? > association);
}