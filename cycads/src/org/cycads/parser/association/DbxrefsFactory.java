/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.general.Messages;
import org.cycads.parser.FileParserError;

public class DbxrefsFactory implements FieldFactory<Dbxrefs>
{
	private String			dbxrefsSeparator;
	private String			delimiter;
	private DbxrefFactory	dbxrefFactory;

	public DbxrefsFactory(String dbxrefsSeparator, String delimiter, DbxrefFactory dbxrefFactory) {
		this.dbxrefsSeparator = dbxrefsSeparator;
		this.delimiter = delimiter;
		this.dbxrefFactory = dbxrefFactory;
	}

	@Override
	public Dbxrefs create(String value) throws FileParserError {
		if (value == null || value.length() == 0) {
			throw new FileParserError(Messages.dbxrefWithoutAccessionException());
		}
		value = cleanTextDelimiter(value);
		Dbxrefs ret = new Dbxrefs();
		String[] dbxrefs = value.split(dbxrefsSeparator);
		for (String dbxref : dbxrefs) {
			ret.add(dbxrefFactory.create(dbxref));
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
