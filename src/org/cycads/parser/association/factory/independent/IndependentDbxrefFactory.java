/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory.independent;

import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.Messages;
import org.cycads.parser.ParserException;
import org.cycads.parser.association.Tools;
import org.cycads.parser.association.TypeNameTransformer;

public class IndependentDbxrefFactory<X extends Dbxref> implements IndependentObjectFactory<X>
{
	private final String					dbxrefSeparator;
	private final TypeNameTransformer		dbNameTransformer;
	private final EntityDbxrefFactory<X>	entityFactory;

	public IndependentDbxrefFactory(String dbxrefSeparator, TypeNameTransformer dbNameTransformer,
			EntityDbxrefFactory<X> entityFactory) {
		this.dbxrefSeparator = dbxrefSeparator;
		this.dbNameTransformer = dbNameTransformer;
		this.entityFactory = entityFactory;
	}

	@Override
	public X create(String value) throws ParserException {
		if (value == null || value.length() == 0) {
			return null;
		}
		String[] strs = Tools.split(value, dbxrefSeparator);
		if (strs == null || strs.length == 0) {
			return null;
		}
		String dbName = null, accession;
		if (strs.length == 1) {
			accession = strs[0];
		}
		else if (strs.length == 2) {
			dbName = strs[0];
			accession = strs[1];
		}
		else {
			throw new ParserException(Messages.invalidDbxrefSplitException(value, dbxrefSeparator));
		}
		dbName = dbNameTransformer.getTypeName(dbName, accession);
		return entityFactory.getDbxref(dbName, accession);
	}

}
