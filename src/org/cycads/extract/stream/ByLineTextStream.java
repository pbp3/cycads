package org.cycads.extract.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

import org.cycads.extract.cyc.CycRecord;
import org.cycads.general.ParametersDefault;

public class ByLineTextStream extends TextStream {

	public ByLineTextStream(String fileOutName, String header) throws FileNotFoundException {
		super(fileOutName,header);
	}

	public ByLineTextStream(File fileOut, String header) throws FileNotFoundException {
		super(fileOut,header);
	}
	
	@Override
	public void print(CycRecord cycRecord) {
		
		Collection<String> contamins = cycRecord.getContamins();
		if (!contamins.isEmpty()) {
			for (String contamin : contamins) {
				if (contamin != null && contamin.length() > 0) {
					out.print(cycRecord.getName() + "\t");
					out.print("putative contaminant: " + contamin.toString());
					out.println();
				}
			}
		}
		// each EC or GO appears on different line(s)
		Collection<String> ecs = cycRecord.getECs();
		if (ecs != null && contamins.isEmpty()) {
			for (String ec : ecs) {
				if (ec != null && ec.length() > 0) {
					out.print(cycRecord.getName() + "\t");
					out.print(ec);
					out.println();
				}
			}
		}
		Collection<String> gos = cycRecord.getGOs();
		if (gos != null && contamins.isEmpty()) {
			for (String go : gos) {
				if (go != null && go.length() > 0) {
					out.print(cycRecord.getName() + "\t");
					out.print(go);
					out.println();
				}
			}
		}
		
		// TODO : Collection<String> kos = cycRecord.getKOs;
		
		out.flush();
	}

	@Override
	public void close() {
		out.close();
	}

}
