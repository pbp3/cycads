/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note;

public interface Type extends Comparable<Type>
{
	public static final String	ENTITY_TYPE_NAME	= "Term_type";

	public String getName();

	public String getDescription();

	public void setDescription(String description);

}
