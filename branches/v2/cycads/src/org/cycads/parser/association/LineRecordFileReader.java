/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.io.BufferedReader;
import java.io.IOException;

import org.cycads.parser.FileParserError;

public class LineRecordFileReader<R> implements RecordFileReader<R>
{

	private BufferedReader		br;
	private String				columnSeparatorRegex;
	private String				commentLineStarter;
	private RecordFactory<R>	recordFactory;

	public LineRecordFileReader(BufferedReader br, String columnSeparator, String commentLineStarter,
			RecordFactory<R> recordFactory) {
		this.columnSeparatorRegex = columnSeparator;
		this.commentLineStarter = commentLineStarter;
		this.br = br;
		this.recordFactory = recordFactory;
	}

	@Override
	public R read() throws IOException, FileParserError {
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.length() > 0 && !line.startsWith(commentLineStarter)) {
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
