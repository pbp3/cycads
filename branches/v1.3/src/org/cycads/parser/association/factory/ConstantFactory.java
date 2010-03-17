/*
 * Created on 23/10/2009
 */
package org.cycads.parser.association.factory;

public class ConstantFactory<R> implements ObjectFactory<R>
{
	R	value;

	public ConstantFactory(R value) {
		this.value = value;
	}

	@Override
	public R create(String[] values) {
		return value;
	}

}
