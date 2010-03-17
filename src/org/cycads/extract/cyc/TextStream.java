package org.cycads.extract.cyc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public abstract class TextStream implements CycStream {

	PrintStream	out;
	String header;
	
	public TextStream(String fileOutName, String header) throws FileNotFoundException {
		this(new File(fileOutName),header);
	}

	public TextStream(File fileOut, String header) throws FileNotFoundException {
		this(new PrintStream(new FileOutputStream(fileOut, false)),header);
	}

	public TextStream(PrintStream out,  String header) {
		this.out = out;
		this.header = header;
		
		if (header != null && header.length() > 0) {
			out.println(header);
		}
	}


	public void flush() {
		out.flush();
	}

}
