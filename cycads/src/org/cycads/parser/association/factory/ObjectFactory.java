/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory;

import org.cycads.parser.ParserException;

public interface ObjectFactory<R>
{
	public R create(String[] values) throws ParserException;

}
