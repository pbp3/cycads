/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import org.cycads.entities.biojava.Method;

public interface MethodFilter
{
	public boolean isValid(Method method);

}
