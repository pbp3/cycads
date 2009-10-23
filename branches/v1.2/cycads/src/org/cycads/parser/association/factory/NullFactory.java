/*
 * Created on 23/10/2009
 */
package org.cycads.parser.association.factory;


public class NullFactory implements ObjectFactory
{

	@Override
	public Object create(String[] values) {
		return null;
	}

}
