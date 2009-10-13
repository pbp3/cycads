/*
 * Created on 13/10/2009
 */
package org.cycads.parser.association;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.parser.ParserException;

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
			String[] strs = value.split(objectsSeparator);
			ret = new ArrayList<R>(strs.length);
			for (String str : strs) {
				ret.add(factory.create(Tools.cleanTextDelimiter(str, objectDelimiter)));
			}
		}
		return ret;
	}

}
