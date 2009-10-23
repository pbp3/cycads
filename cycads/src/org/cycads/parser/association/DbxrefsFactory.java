/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.Collection;

import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.ParserException;
import org.cycads.parser.association.factory.ObjectFactory;
import org.cycads.parser.association.factory.independent.IndependentDbxrefFactory;

public class DbxrefsFactory<X extends Dbxref> implements ObjectFactory<Collection<X>>
{
	private String	dbxrefsSeparator;
	private String	delimiter;

	public DbxrefsFactory(String dbxrefsSeparator, String delimiter, IndependentDbxrefFactory<X> dbxrefFieldFactory) {
		this.dbxrefsSeparator = dbxrefsSeparator;
		this.delimiter = delimiter;
		this.dbxrefFieldFactory = dbxrefFieldFactory;
	}

	@Override
	public Collection<X> create(String[] values) throws ParserException {
		value = cleanTextDelimiter(value);
		DbxrefsField<X> ret = new DbxrefsField<X>();
		if (value != null && value.length() != 0) {
			String[] dbxrefs = Tools.split(value, dbxrefsSeparator);
			for (String dbxref : dbxrefs) {
				ret.add(dbxrefFieldFactory.create(dbxref));
			}
		}
		return ret;
	}

	private String cleanTextDelimiter(String text) {
		if (text == null || text.length() == 0 || delimiter == null || delimiter.length() == 0) {
			return text;
		}
		int start = 0, end = text.length();
		if (text.startsWith(delimiter)) {
			start = delimiter.length();
		}
		if (text.endsWith(delimiter)) {
			end = end - delimiter.length();
		}
		return text.substring(start, end);
	}

}
