/*
 * Created on 28/09/2009
 */
package org.cycads.entities;

import org.cycads.entities.annotation.AssociationObject;

public interface Feature extends AssociationObject
{
	public static final String	OBJECT_TYPE_NAME	= "Feature";

	public String getName();

	public String getDescription();

}
