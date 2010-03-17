/*
 * Created on 28/09/2009
 */
package org.cycads.entities;

public interface Feature extends BasicEntity
{
	public static final String	ENTITY_TYPE_NAME	= "Feature";

	public String getName();

	public String getDescription();

}
