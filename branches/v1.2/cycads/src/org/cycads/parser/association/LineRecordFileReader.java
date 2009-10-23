/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.cycads.parser.FileParserException;
import org.cycads.parser.ParserException;
import org.cycads.parser.association.factory.ObjectFactory;

public class LineRecordFileReader<R> implements RecordFileReader<R>
{

	private BufferedReader		br;
	private String				columnSeparatorRegex;
	private String				commentLineStarter;
	private ObjectFactory<R>	objectFactory;
	private Pattern				removeLinePattern;

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
}