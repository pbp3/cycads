/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.cycads.general.ParametersDefault;
import org.cycads.parser.FileParserException;
import org.cycads.parser.ParserException;
import org.cycads.parser.association.factory.ObjectFactory;
import org.cycads.ui.progress.Progress;

public class LineRecordFileReader<R> implements RecordFileReader<R>
{
	private final BufferedReader	br;
	private final String			columnSeparatorRegex;
	private final String			commentLineStarter;
	private final ObjectFactory<R>	objectFactory;
	private final Pattern			removeLinePattern;

	public LineRecordFileReader(BufferedReader br, String columnSeparatorRegex, String commentLineStarter,
			Pattern removeLinePattern, ObjectFactory<R> recordFactory) {
		this.br = br;
		this.columnSeparatorRegex = columnSeparatorRegex;
		this.commentLineStarter = commentLineStarter;
		this.objectFactory = recordFactory;
		this.removeLinePattern = removeLinePattern;
	}

	@Override
	public R read() throws IOException, FileParserException {
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.length() > 0 && !line.startsWith(commentLineStarter) && !removeLinePattern.matcher(line).matches()) {
				try {
					return objectFactory.create(Tools.split(line, columnSeparatorRegex));
				}
				catch (ParserException e) {
					throw new FileParserException("Line=" + line, e);
				}
			}
		}
		return null;
	}

	@Override
	public void readAll(Progress progressOK, Progress progressError) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.length() > 0 && !line.startsWith(commentLineStarter) && !removeLinePattern.matcher(line).matches()) {
				try {
					objectFactory.create(Tools.split(line, columnSeparatorRegex));
					progressOK.completeStep();
				}
				catch (ParserException e) {
					if (ParametersDefault.isDebugging()) {
						e.printStackTrace();
					}
					progressError.completeStep();
				}
			}
		}
	}

}
