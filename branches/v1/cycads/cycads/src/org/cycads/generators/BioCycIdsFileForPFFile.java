/*
 * Created on 19/06/2008
 */
package org.cycads.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.cycads.general.ParametersDefault;

public class BioCycIdsFileForPFFile
{
	File		file;
	PrintStream	out;
	String		separator	= ParametersDefault.bioCycIdsSeparator();

	public BioCycIdsFileForPFFile(String fileOutName, String header) throws FileNotFoundException {
		this(new File(fileOutName), header);
	}

	public BioCycIdsFileForPFFile(File file, String header) throws FileNotFoundException {
		this.out = new PrintStream(file);
		out.print(header);
		this.file = file;
	}

	public void write(String productId, String bioCycId) {
		out.println(productId + separator + bioCycId);
	}

	public void restart() throws FileNotFoundException {
		out.close();
		out = new PrintStream(new FileOutputStream(file, true));
	}

}
