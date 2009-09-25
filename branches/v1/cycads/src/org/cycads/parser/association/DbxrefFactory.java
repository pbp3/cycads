/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.entities.EntityFactory;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Messages;
import org.cycads.parser.FileParserError;

public class DbxrefFactory implements FieldFactory<Dbxref>
{
	private String				separator;
	private String				delimiter;
	private DbnameTransformer	dbNameTransformer;
	private EntityFactory		entityFactory;

	public DbxrefFactory(String separator, String delimiter, DbnameTransformer dbNameTransformer,
			EntityFactory entityFactory) {
		this.separator = separator;
		this.delimiter = delimiter;
		this.dbNameTransformer = dbNameTransformer;
		this.entityFactory = entityFactory;
	}

	@Override
	public Dbxref create(String value) throws FileParserError {
		if (value == null || value.length() == 0) {
			throw new FileParserError(Messages.dbxrefWithoutAccessionException());
		}
		String[] strs = value.split(separator);
		String dbName = null, accession;
		if (strs.length == 1) {
			accession = strs[0];
		}
		else if (strs.length == 2) {
			dbName = strs[0];
			accession = strs[1];
		}
		else {
			throw new FileParserError(Messages.dbxrefWithoutAccessionException());
		}
		dbName = dbNameTransformer.getDbname(dbName, accession);
		return entityFactory.getDbxref(dbName, accession);
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
