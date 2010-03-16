/*
 * Created on 19/06/2008
 */
package org.cycads.extract.cyc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

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
			printComment(header);
		}
		this.sequenceLocation = sequenceLocation;
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

		Collection<String> gos = cycRecord.getGOs();
		if (gos != null) {
			for (String go : gos) {
				if (go != null && go.length() > 0) {
					out.println("DBLINK" + "\t\t" + "GO:" + go);
				}
			}
		}

		Collection<CycDBLink> dbLinks = cycRecord.getDBLinks();
		if (dbLinks != null) {
			for (CycDBLink dbLink : dbLinks) {
				out.println("DBLINK" + "\t\t" + dbLink.getDbName() + ":" + dbLink.getAccession());
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

	public void printGeneECGO(CycRecord cycRecord, String fileformat) {
		
		if (fileformat.equals("byECorGO")){ // each EC or GO appears on different line(s)
			Collection<String> ecs = cycRecord.getECs();
			if (ecs != null) {
				boolean first = true;
				for (String ec : ecs) {
					if (ec != null && ec.length() > 0) {
						out.print(cycRecord.getName() + "\t");
						out.print(ec);
						out.println();
					}
				}
			}
			Collection<String> gos = cycRecord.getGOs();
			if (gos != null) {
				boolean first = true;
				for (String go : gos) {
					if (go != null && go.length() > 0) {
						out.print(cycRecord.getName() + "\t");
						out.print("GO:" + go);
						out.println();
					}
				}
			}	
		}
		else { // default format, ECs ou GOs are concat (with ';' separator in their respective column)
			out.print(cycRecord.getName() + "\t");
			Collection<String> ecs = cycRecord.getECs();
			if (ecs != null) {
				boolean first = true;
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
				boolean first = true;
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
			out.println();
		}
		out.flush();
	}
	
}


