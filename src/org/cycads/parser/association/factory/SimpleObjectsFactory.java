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

	private int							index;
	private String						columnDelimiter;
	private String						objectsSeparator;
	private String						objectDelimiter;
	private IndependentObjectFactory<R>	factory;

	public SimpleObjectsFactory(int index, String columnDelimiter, String objectsSeparator, String objectDelimiter,
			IndependentObjectFactory<R> factory) {
		this.index = index;
		this.columnDelimiter = columnDelimiter;
		this.objectsSeparator = objectsSeparator;
		this.objectDelimiter = objectDelimiter;
		this.factory = factory;
	}

	@Override
	public Collection<R> create(String[] values) throws ParserException {
		Collection<R> ret;
		if (index < 0 || index >= values.length) {
			return null;
		}
		String value = Tools.cleanTextDelimiter(values[index], columnDelimiter);
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
