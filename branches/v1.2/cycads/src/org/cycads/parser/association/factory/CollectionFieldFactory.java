/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.parser.ParserException;
import org.cycads.parser.association.Tools;
import org.cycads.parser.association.factory.independent.IndependentObjectFactory;

public class CollectionFieldFactory<R> implements IndependentObjectFactory<Collection<R>>
{
	private String						objectSeparator;
	private String						delimiter;
	private IndependentObjectFactory<R>	independentFactory;

	public CollectionFieldFactory(String objectSeparator, String delimiter,
			IndependentObjectFactory<R> independentFactory) {
		this.objectSeparator = objectSeparator;
		this.delimiter = delimiter;
		this.independentFactory = independentFactory;
	}

	@Override
	public Collection<R> create(String value) throws ParserException {
		value = Tools.cleanTextDelimiter(value, delimiter);
		Collection<R> ret = new ArrayList<R>();
		String[] objs = Tools.split(value, objectSeparator);
		for (String obj : objs) {
			ret.add(independentFactory.create(obj));
		}
		return ret;
	}

}
