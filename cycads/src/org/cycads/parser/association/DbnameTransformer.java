/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.parser.FileParserError;

public interface DbnameTransformer
{
	public static final String	DBNAME_GENERIC	= "*";

	public String getDbname(String dbName, String accession) throws FileParserError;
}
