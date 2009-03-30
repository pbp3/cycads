/*
 * Created on 19/06/2008
 */
package org.cycads.extract.cyc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;

import org.cycads.general.ParametersDefault;

public class PFFileStream implements CycStream
{
	PrintStream	out;

	public PFFileStream(String fileOutName, String header) throws FileNotFoundException {
		this(new File(fileOutName), header);
	}

	public PFFileStream(File fileOut, String header) throws FileNotFoundException {
		out = new PrintStream(new FileOutputStream(fileOut, false));
		if (header != null && header.length() > 0) {
			printComment(header);
		}
	}

	public void printComment(String str) {
		out.println(ParametersDefault.getPFFileCommentStart() + str);
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
		for (CycFunction function : functions) {
			out.println("FUNCTION" + "\t" + function.getName());
			StringBuffer comment = new StringBuffer();
			for (String fComment : function.getComments()) {
				comment.append(fComment);
				comment.append(ParametersDefault.getPFFileFunctionCommentSeparator());
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

		Collection<String> ecs = cycRecord.getECs();
		if (ecs != null) {
			for (String ec : ecs) {
				if (ec != null && ec.length() > 0) {
					out.println("EC" + "\t\t" + ec);
				}
			}
		}

		Collection<CycDBLink> dbLinks = cycRecord.getDBLinks();
		if (dbLinks != null) {
			for (CycDBLink dbLink : dbLinks) {
				out.println("DBLINK" + "\t\t" + dbLink.getDbName() + ":" + dbLink.getAccession());
			}
		}

		out.println("STARTBASE" + "\t" + cycRecord.getStartBase());
		out.println("ENDBASE" + "\t\t" + cycRecord.getEndBase());
		Collection< ? extends CycIntron> introns = cycRecord.getIntrons();
		if (introns != null) {
			for (CycIntron intron : introns) {
				out.println("INTRON" + "\t\t" + intron.getStart() + "-" + intron.getEnd());
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

		Collection<String> comments = cycRecord.getComments();
		StringBuffer commentAll = new StringBuffer();
		for (String comment : comments) {
			if (comment != null && comment.length() > 0) {
				commentAll.append(comment);
				commentAll.append(ParametersDefault.getPFFileGeneCommentSeparator());
			}
		}
		if (commentAll.length() > 0) {
			out.println("GENE-COMMENT" + "\t" + commentAll.substring(0, commentAll.length() - 1));
		}

		out.println("//");
		out.println();
		out.flush();
	}

	public void flush() {
		out.flush();
	}

}
