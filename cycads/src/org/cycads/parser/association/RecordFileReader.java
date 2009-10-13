/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.io.IOException;

import org.cycads.parser.FileParserException;

public interface RecordFileReader<R>
{
	R read() throws IOException, FileParserException;
}
