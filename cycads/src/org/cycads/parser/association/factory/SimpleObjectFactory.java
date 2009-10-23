/*
 * Created on 13/10/2009
 */
package org.cycads.parser.association.factory;

import org.cycads.parser.ParserException;
import org.cycads.parser.association.Tools;
import org.cycads.parser.association.factory.independent.IndependentObjectFactory;

public class SimpleObjectFactory<R> implements ObjectFactory<R>
{

	int							index;
	String						delimiter;
	IndependentObjectFactory<R>	factory;

	public SimpleObjectFactory(int index, String delimiter, IndependentObjectFactory<R> factory) {
		this.index = index;
		this.delimiter = delimiter;
		this.factory = factory;
	}

	@Override
	public R create(String[] values) throws ParserException {
		return factory.create(Tools.cleanTextDelimiter(values[index], delimiter));
	}

}
