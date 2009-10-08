/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.cycads.parser.FileParserError;

public class LineRecordFileReader<R> implements RecordFileReader<R>
{

	private BufferedReader		br;
	private String				columnSeparatorRegex;
	private String				commentLineStarter;
	private RecordFactory<R>	recordFactory;
	private Pattern				removeLinePattern;

	public LineRecordFileReader(BufferedReader br, String columnSeparatorRegex, String commentLineStarter,
			Pattern removeLinePattern, RecordFactory<R> recordFactory) {
		this.br = br;
		this.columnSeparatorRegex = columnSeparatorRegex;
		this.commentLineStarter = commentLineStarter;
		this.recordFactory = recordFactory;
		this.removeLinePattern = removeLinePattern;
	}

	@Override
	public R read() throws IOException, FileParserError {
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.length() > 0 && !line.startsWith(commentLineStarter) && !removeLinePattern.matcher(line).matches()) {
				try {
					return recordFactory.create(line.split(columnSeparatorRegex));
				}
				catch (FileParserError e) {
					throw new FileParserError("Line=" + line, e);
				}
			}
		}
		return null;
	}
}
