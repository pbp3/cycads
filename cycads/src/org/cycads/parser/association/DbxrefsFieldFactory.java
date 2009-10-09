/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.entities.synonym.Dbxref;
import org.cycads.parser.FileParserError;

public class DbxrefsFieldFactory<X extends Dbxref> implements FieldFactory<DbxrefsField<X>>
{
	private String					dbxrefsSeparator;
	private String					delimiter;
	private DbxrefFieldFactory<X>	dbxrefFieldFactory;

	public DbxrefsFieldFactory(String dbxrefsSeparator, String delimiter, DbxrefFieldFactory<X> dbxrefFieldFactory) {
		this.dbxrefsSeparator = dbxrefsSeparator;
		this.delimiter = delimiter;
		this.dbxrefFieldFactory = dbxrefFieldFactory;
	}

	@Override
	public DbxrefsField<X> create(String value) throws FileParserError {
		value = cleanTextDelimiter(value);
		DbxrefsField<X> ret = new DbxrefsField<X>();
		if (value != null && value.length() != 0) {
			String[] dbxrefs = value.split(dbxrefsSeparator);
			for (String dbxref : dbxrefs) {
				ret.add(dbxrefFieldFactory.create(dbxref));
			}
		}
		return ret;
	}

	private String cleanTextDelimiter(String text) {
		if (text == null || text.length() == 0) {
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
