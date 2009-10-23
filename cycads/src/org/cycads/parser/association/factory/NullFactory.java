/*
 * Created on 23/10/2009
 */
package org.cycads.parser.association.factory;

import org.cycads.parser.association.ObjectFactory;

public class NullFactory implements ObjectFactory
{

	@Override
	public Object create(String[] values) {
		return null;
	}

}
