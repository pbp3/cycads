package org.cycads.extract.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

import org.cycads.extract.cyc.CycFunction;
import org.cycads.extract.cyc.CycRecord;
import org.cycads.general.ParametersDefault;

public class ByFunctionTextStream extends TextStream {
	
	public ByFunctionTextStream(String fileOutName, String header) throws FileNotFoundException {
		super(fileOutName,header);
	}

	public ByFunctionTextStream(File fileOut, String header) throws FileNotFoundException {
		super(fileOut,header);
	}
	
	@Override
	public void print(CycRecord cycRecord) {
		
		boolean first;
		
		// Function, ECs ou GOs are concat (with ';' separator in their respective column)
		out.print(cycRecord.getName() + "\t");
		Collection<CycFunction> functions = cycRecord.getFunctions();
		first = true;
		for (CycFunction function : functions) {
			if (!first) {
				out.print(";");
			}
			out.print(function.getName());
			first = false;
		}
		out.print("\t");
			
		Collection<String> ecs = cycRecord.getECs();
		if (ecs != null) {
			first = true;
			for (String ec : ecs) {
				if (ec != null && ec.length() > 0) {
					if (!first) {
						out.print(";");
						out.print(ec);
					}
					else {
						out.print(ec);
						first = false;
					}
				}
			}
		}

		out.print("\t");
		Collection<String> gos = cycRecord.getGOs();
		if (gos != null) {
			first = true;
			for (String go : gos) {
				if (go != null && go.length() > 0) {
					if (!first) {
						out.print(";");
						out.print("GO:");
						out.print(go);
					}
					else {
						out.print("GO:");
						out.print(go);
						first = false;
					}
				}
			}
		}
		
		// TODO : Collection<String> kos = cycRecord.getKOs;
		
		out.println();
		out.flush();
	}

	@Override
	public void close() {
		out.close();
	}

}

