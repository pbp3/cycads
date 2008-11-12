/*
 * Created on 20/10/2008
 */
package org.cycads.entities.biojava1;

import java.util.Collection;

public interface MethodType
{
	public String getName();

	public Method getOrCreateMethod(String name);

	public Collection<Method> getMethods();
}
