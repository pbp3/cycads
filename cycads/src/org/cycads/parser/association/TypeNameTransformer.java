/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.parser.FileParserError;

public interface TypeNameTransformer
{
	public static final String	TYPE_NAME_GENERIC	= "*";

	public String getTypeName(String typeName, String value) throws FileParserError;
}
