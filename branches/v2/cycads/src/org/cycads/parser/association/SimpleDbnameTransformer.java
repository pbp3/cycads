/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.general.Messages;
import org.cycads.parser.FileParserError;

public class SimpleDbnameTransformer implements DbnameTransformer
{
	String	dbNameDefault;

	public SimpleDbnameTransformer(String dbNameDefault) {
		if (dbNameDefault == null || dbNameDefault.length() == 0 || dbNameDefault.equals(this.DBNAME_GENERIC)) {
			dbNameDefault = null;
		}
		this.dbNameDefault = dbNameDefault;
	}

	@Override
	public String getDbname(String dbName, String accession) throws FileParserError {
		if (dbName == null || dbName.length() == 0) {
			if (dbNameDefault == null || dbNameDefault.length() == 0) {
				throw new FileParserError(Messages.dbxrefWithoutDBNameException());
			}
			return dbNameDefault;
		}
		return dbName;
	}
}
