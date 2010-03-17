/*
 * Created on 23/10/2009
 */
package org.cycads.parser.association.factory;

public class NullFactory<R> implements ObjectFactory<R>
{

	@Override
	public R create(String[] values) {
		return null;
	}

}
