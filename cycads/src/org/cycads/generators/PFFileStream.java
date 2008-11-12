/*
 * Created on 19/06/2008
 */
package org.cycads.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;

import org.cycads.entities.biojava1.DBLink;
import org.cycads.entities.biojava1.Function;

public class PFFileStream implements BioCycStream
{
	PrintStream	out;
	File		file;

	public PFFileStream(String fileOutName, String header) throws FileNotFoundException {
		this(new File(fileOutName), header);
	}

	public PFFileStream(File fileOut, String header) throws FileNotFoundException {
		this.file = fileOut;
		out = new PrintStream(new FileOutputStream(fileOut, false));
		if (header != null && header.length() > 0) {
			println(header);
		}
	}

	public void println(String str) {
		out.println(str);
	}

	public void restart() throws FileNotFoundException {
		out.close();
		out = new PrintStream(new FileOutputStream(file, true));
	}

	public void write(BioCycRecord geneRecord) throws GeneRecordInvalidException {
		if (geneRecord == null || !geneRecord.isValid()) {
			throw new GeneRecordInvalidException(geneRecord);
		}
		out.println("ID" + "\t\t" + geneRecord.getId());
		out.println("NAME" + "\t\t" + geneRecord.getName());
		out.println("PRODUCT-TYPE" + "\t" + geneRecord.getType());
		String productID = geneRecord.getProductID();
		if (productID != null && productID.length() != 0) {
			out.println("PRODUCT-ID" + "\t" + productID);
		}

		Collection<Function> functions = geneRecord.getFunctions();
		String synonym;
		for (Function function : functions) {
			out.println("FUNCTION" + "\t" + function.getName());
			String comment = function.getComment();
			if (comment != null && comment.length() > 0) {
				out.println("FUNCTION-COMMENT" + "\t" + comment);
			}
			synonym = function.getSynonym();
			if (synonym != null && synonym.length() > 0) {
				out.println("FUNCTION-SYNONYM" + "\t" + synonym);
			}
		}

		Collection<String> ecs = geneRecord.getECs();
		if (ecs != null) {
			for (String ec : ecs) {
				if (ec != null && ec.length() > 0) {
					out.println("EC" + "\t\t" + ec);
				}
			}
		}

		Collection<DBLink> dbLinks = geneRecord.getDBLinks();
		if (dbLinks != null) {
			for (DBLink dbLink : dbLinks) {
				out.println("DBLINK" + "\t\t" + dbLink.getType() + ":" + dbLink.getValue());
			}
		}

		out.println("STARTBASE" + "\t" + geneRecord.getStartBase());
		out.println("ENDBASE" + "\t\t" + geneRecord.getEndBase());
		Collection<Intron> introns = geneRecord.getIntrons();
		if (introns != null) {
			for (Intron intron : introns) {
				out.println("INTRON" + "\t\t" + intron.getBegin() + "-" + intron.getEnd());
			}
		}

		Collection<String> synonyms = geneRecord.getSynonyms();
		if (synonyms != null) {
			for (String syn : synonyms) {
				if (syn != null && syn.length() > 0) {
					out.println("SYNONYM" + "\t\t" + syn);
				}
			}
		}

		Collection<String> comments = geneRecord.getComments();
		boolean first = true;
		for (String comment : comments) {
			if (comment != null && comment.length() > 0) {
				if (first) {
					out.print("GENE-COMMENT");
					first = false;
				}
				else {
					out.println("; ");
				}
				out.print("\t" + comment);
			}
		}
		if (!first) {
			out.println();
		}

		out.println("//");
		out.println();
		out.flush();
	}

	public void flush() {
		out.flush();
	}

}
