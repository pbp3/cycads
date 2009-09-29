/*
 * Created on 19/06/2008
 */
package org.cycads.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.cycads.general.CacheCleanerListener;

public class FastaFileForPFFile implements CacheCleanerListener
{
	File		file;
	int			lineWidth;
	PrintStream	out;

	public FastaFileForPFFile(String fileOutName, String header, int lineWidth) throws FileNotFoundException {
		this(new File(fileOutName), header, lineWidth);
	}

	public FastaFileForPFFile(File file, String header, int lineWidth) throws FileNotFoundException {
		this.out = new PrintStream(file);
		out.print(header);
		this.file = file;
		this.lineWidth = lineWidth;
	}

	public int write(String stringSeq) {
		//		String stringSeq = BioSql.getStringSeq(seqId);
		int length = stringSeq.length();

		for (int pos = 0; pos < length; pos++) {
			if (pos % lineWidth == 0) {
				out.println();
			}
			out.print(stringSeq.charAt(pos));
		}
		out.flush();
		return length;
	}

	public void restart() {
		out.close();
		try {
			out = new PrintStream(new FileOutputStream(file, true));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void flush() {
		out.flush();
	}

	public void clearCache() {
		restart();
	}

}
