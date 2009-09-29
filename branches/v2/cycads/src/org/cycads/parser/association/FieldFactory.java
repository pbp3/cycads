/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.parser.FileParserError;

public interface FieldFactory<F>
{
	public F create(String value) throws FileParserError;
}
