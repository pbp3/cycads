/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note;

public interface Type extends Comparable<Type>
{
	public String getName();

	public String getDescription();

	public void setDescription(String description);

}
