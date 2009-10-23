/*
 * Created on 23/10/2009
 */
package org.cycads.parser.association.factory;


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
