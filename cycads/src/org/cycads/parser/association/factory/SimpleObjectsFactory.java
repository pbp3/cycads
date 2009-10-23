/*
 * Created on 13/10/2009
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.parser.ParserException;
import org.cycads.parser.association.Tools;
import org.cycads.parser.association.factory.independent.IndependentObjectFactory;

public class SimpleObjectsFactory<R> implements ObjectFactory<Collection<R>>
{

	int							index;
	String						delimiter;
	String						objectsSeparator;
	String						objectDelimiter;
	IndependentObjectFactory<R>	factory;

	public SimpleObjectsFactory(int index, String delimiter, String objectsSeparator, String objectDelimiter,
			IndependentObjectFactory<R> factory) {
		this.index = index;
		this.delimiter = delimiter;
		this.objectsSeparator = objectsSeparator;
		this.objectDelimiter = objectDelimiter;
		this.factory = factory;
	}

	@Override
	public Collection<R> create(String[] values) throws ParserException {
		Collection<R> ret;
		String value = Tools.cleanTextDelimiter(values[index], delimiter);
		if (objectsSeparator == null || objectsSeparator.length() == 0) {
			ret = new ArrayList<R>(1);
			ret.add(factory.create(value));
		}
		else {
			String[] strs = Tools.split(value, objectsSeparator);
			ret = new ArrayList<R>(strs.length);
			for (String str : strs) {
				ret.add(factory.create(Tools.cleanTextDelimiter(str, objectDelimiter)));
			}
		}
		return ret;
	}

}
