/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Messages;
import org.cycads.parser.FileParserError;

public class DbxrefFactory implements FieldFactory<Dbxref>
{
	private String				dbxrefSeparator;
	private DbnameTransformer	dbNameTransformer;
	private EntityDbxrefFactory	entityFactory;

	public DbxrefFactory(String dbxrefSeparator, DbnameTransformer dbNameTransformer, EntityDbxrefFactory entityFactory) {
		this.dbxrefSeparator = dbxrefSeparator;
		this.dbNameTransformer = dbNameTransformer;
		this.entityFactory = entityFactory;
	}

	@Override
	public Dbxref create(String value) throws FileParserError {
		if (value == null || value.length() == 0) {
			throw new FileParserError(Messages.dbxrefWithoutAccessionException());
		}
		String[] strs = value.split(dbxrefSeparator);
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

}
