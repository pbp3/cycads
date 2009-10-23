/*
 * Created on 23/10/2009
 */
package org.cycads.parser.association.factory;

import org.cycads.parser.association.ObjectFactory;

public class ConstantFactory implements ObjectFactory<String>
{
	String	value;

	public ConstantFactory(String value) {
		this.value = value;
	}

	@Override
	public String create(String[] values) {
		return value;
	}

}
