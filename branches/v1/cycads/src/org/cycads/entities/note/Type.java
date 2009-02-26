/*
 * Created on 23/02/2009
 */
package org.cycads.entities.note;

import java.util.Collection;

public interface Type
{
	public String getName();

	public String getDescription();

	public Type getParent();

	public Collection<Type> getChildren();
}
