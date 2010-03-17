/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.io.IOException;

import org.cycads.parser.FileParserException;
import org.cycads.ui.progress.Progress;

public interface RecordFileReader<R>
{
	public R read() throws IOException, FileParserException;

	public void readAll(Progress progressOK, Progress progressError) throws IOException;
}
