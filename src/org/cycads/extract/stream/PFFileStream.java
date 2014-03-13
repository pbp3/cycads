/*
 * Created on 19/06/2008
 */
package org.cycads.extract.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

import org.cycads.extract.cyc.CycFunction;
import org.cycads.extract.cyc.CycIntron;
import org.cycads.extract.cyc.CycRecord;
import org.cycads.extract.cyc.CycStream;
import org.cycads.general.ParametersDefault;

public class PFFileStream implements CycStream
{
	PrintStream	out;
	boolean		sequenceLocation	= true;

	public PFFileStream(String fileOutName, String header, boolean sequenceLocation) throws FileNotFoundException {
		this(new File(fileOutName), header, sequenceLocation);
	}

	public PFFileStream(File fileOut, String header, boolean sequenceLocation) throws FileNotFoundException {
		out = new PrintStream(new FileOutputStream(fileOut, false));
		if (header != null && header.length() > 0) {
			out.println(header);
		}
		this.sequenceLocation = sequenceLocation;
	}

	public void print(CycRecord cycRecord) {
		if (cycRecord == null) {
			return;
		}
		out.println("ID" + "\t\t" + cycRecord.getId());
		out.println("NAME" + "\t\t" + cycRecord.getName());
		out.println("PRODUCT-TYPE" + "\t" + cycRecord.getProductType());
		//		String productID = geneRecord.getProductID();
		//		if (productID != null && productID.length() != 0) {
		//			out.println("PRODUCT-ID" + "\t" + productID);
		//		}

		Collection<CycFunction> functions = cycRecord.getFunctions();
		if (functions.isEmpty()) {
			out.println("FUNCTION" + "\t" + "ORF"); // PBP : The word ORF should be used if the function is unknown.
		}
		else  {
			for (CycFunction function : functions) {
				out.println("FUNCTION" + "\t" + function.getName());
				StringBuffer comment = new StringBuffer();
				for (String fComment : function.getComments()) {
					if (comment.length() > 0) {
						comment.append(ParametersDefault.getPFFileFunctionCommentSeparator());
					}
					comment.append(fComment);
				}
				if (comment.length() > 0) {
					out.println("FUNCTION-COMMENT" + "\t" + comment.substring(0, comment.length() - 1));
				}
				for (String synonym : function.getSynonyms()) {
					if (synonym != null && synonym.length() > 0) {
						out.println("FUNCTION-SYNONYM" + "\t" + synonym);
					}
				}
			}
		}

		Collection<String> ecs = cycRecord.getECs();
		if (ecs != null) {
			for (String ec : ecs) {
				if (ec != null && ec.length() > 0) {
					out.println("EC" + "\t\t" + ec);
				}
			}
		}
/* See bellow, output changed according to pathologic file format : out.println("GO" + "\t" + go);
		Collection<String> gos = cycRecord.getGOs();
		if (gos != null) {
			for (String go : gos) {
				if (go != null && go.length() > 0) {
					out.println("DBLINK" + "\t\t" + "GO:" + go);
				}
			}
		}
*/

		Collection<String> dbLinks = cycRecord.getDBLinks();
		if (dbLinks != null) {
			for (String dbLink : dbLinks) {
				out.println("DBLINK" + "\t\t" + dbLink);
			}
		}

		if (sequenceLocation) {
			out.println("STARTBASE" + "\t" + cycRecord.getStartBase());
			out.println("ENDBASE" + "\t\t" + cycRecord.getEndBase());
			Collection< ? extends CycIntron> introns = cycRecord.getIntrons();
			if (introns != null) {
				for (CycIntron intron : introns) {
					out.println("INTRON" + "\t\t" + intron.getStart() + "-" + intron.getEnd());
				}
			}
		}

		Collection<String> synonyms = cycRecord.getSynonyms();
		if (synonyms != null) {
			for (String syn : synonyms) {
				if (syn != null && syn.length() > 0) {
					out.println("SYNONYM" + "\t\t" + syn);
				}
			}
		}
		
		// PBP: according to pathologic file format,
		// GO should be written else with DBLINK tag and GO:id
		// or GO tag and GoName|GOid|Citation PubMed ID|Evidence Code
		Collection<String> gos = cycRecord.getGOs();
		if (gos != null) {
			for (String go : gos) {
				if (go != null && go.length() > 0) {
					out.println("GO" + "\t" + "|" + go.replace("GO:", "") + "||IEA");
				}
			}
		}
		// PBP: separate phylome GOs with separate goMsg
		Collection<String> phygos = cycRecord.getPhyGOs();
		if (phygos != null) {
			for (String phygo : phygos) {
				if (phygo != null && phygo.length() > 0 && !gos.contains(phygo)) {
					out.println("GO" + "\t" + "|" + phygo.replace("GO:", "") + "||IEA");
				}
			}
		}

		Collection<String> comments = cycRecord.getComments();
		StringBuffer commentAll = new StringBuffer();
		if (comments != null && !comments.isEmpty()) {
			Iterator<String> iterator = comments.iterator();
			commentAll.append(iterator.next());
			while (iterator.hasNext()) {
				commentAll.append(ParametersDefault.getPFFileGeneCommentSeparator());
				commentAll.append(iterator.next());
			}
		}
		if (commentAll.length() > 0) {
			out.println("GENE-COMMENT" + "\t" + commentAll);
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
