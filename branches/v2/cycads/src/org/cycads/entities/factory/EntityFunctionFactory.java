/*
 * Created on 28/09/2009
 */
package org.cycads.entities.factory;

import org.cycads.entities.Function;

public interface EntityFunctionFactory<F extends Function>
{
	public F getFunction(String name, String description);

}
