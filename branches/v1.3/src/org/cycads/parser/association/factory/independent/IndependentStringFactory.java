/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory.independent;


public class IndependentStringFactory implements IndependentObjectFactory<String>
{
	@Override
	public String create(String value) {
		return value;
	}

}
