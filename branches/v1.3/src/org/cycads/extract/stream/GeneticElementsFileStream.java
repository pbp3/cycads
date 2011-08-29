package org.cycads.extract.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import org.cycads.entities.sequence.Sequence;
import org.cycads.general.Config;


public class GeneticElementsFileStream {
	PrintStream	out;
	String header;
	String fileName = Config.annotationGeneratorGeneticElementsFileName();

	public GeneticElementsFileStream (String fileOutName, String header) throws FileNotFoundException {
		this(new File(fileOutName),header);
	}

	public GeneticElementsFileStream (File fileOut, String header) throws FileNotFoundException {
		this(new PrintStream(new FileOutputStream(fileOut, false)),header);
	}

	public GeneticElementsFileStream(PrintStream out,  String header) {
		this.out = out;
		this.header = header;
	
		if (header != null && header.length() > 0) {
			out.println(header);
		}
	}


	/*print a contig record for the genetic-elements.dat file
	 * ID	GL363747
	 * NAME	Contig GL363747
	 * TYPE	:CONTIG
	 * ANNOT-FILE	GL363747.pf
	 */
	public void printContig(Sequence< ? , ? > seq, String annotFileName, String seqfile) {
		if (seq == null) {
			return;
		}
		out.println("ID" + "\t\t" + seq.getDbName()+"-"+seq.getAccession());
		out.println("NAME" + "\t\t" + seq.getAccession());
		out.println("TYPE" + "\t" + ":CONTIG");
		out.println("ANNOT-FILE" + "\t" + annotFileName);
		if (seqfile == "y") {
			out.println("SEQ-FILE" + "\t" + seq.getAccession() + "." + Config.annotationGeneratorseqFilesNameSuffix());
		}
		out.println("//");
		out.flush();
	}
	
	/*print a contig list for the genetic-elements.dat file
	 * ID	ACYPI_V2
	 * NAME	Genome Assembly Acyr2.0
	 * TYPE	:CHRSM
	 * CIRCULAR?	N
	 * SEQ-FILE	assembly2_scaffolds.fasta
	 * CONTIG	GL363747
	*/
	public void printContigsList(String genElemID, String genElemName, String seqFileName, Collection<Sequence< ? , ? >> seqs) {
		if (seqs == null) {
			return;
		}
		out.println("ID" + "\t\t" + genElemID);
		out.println("NAME" + "\t\t" + genElemName);
		out.println("TYPE" + "\t" + ":CHRSM");
		out.println("CIRCULAR?" + "\t" + "N");
		if (Config.annotationGeneratorMultipleSequencesFiles() == "n") {
			out.println("SEQ-FILE" + "\t" + seqFileName);
		}
		for (Sequence< ? , ? > seq : seqs) {
			out.println("CONTIG" + "\t" + seq.getDbName()+"-"+seq.getAccession());
		}
		out.println("//");
		out.println();
		out.flush();
	}

	public void flush() {
		out.flush();
	}
	
	public void close() {
		out.close();
	}

}
	
