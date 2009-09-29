/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.parser.FileParserError;

public interface RecordFactory<R>
{
	public R create(String[] values) throws FileParserError;

}
