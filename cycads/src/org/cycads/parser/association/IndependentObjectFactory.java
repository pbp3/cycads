/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.parser.ParserException;

public interface IndependentObjectFactory<R>
{
	public R create(String value) throws ParserException;

}
