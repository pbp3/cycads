/*
 * Created on 06/01/2009
 */
package beta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.cycads.dbExternal.EC;
import org.cycads.dbExternal.KO;

public class PFFileGenerator
{
	PrintStream	out;

	public PFFileGenerator(PrintStream out) {
		this.out = out;
	}

	public PFFileGenerator(File fileOut) throws FileNotFoundException {
		this(new PrintStream(new FileOutputStream(fileOut, false)));
	}

	public void write(CDSSQL cds) {

		out.println("ID" + "\t\t" + cds.getAcypi());
		out.println("NAME" + "\t\t" + cds.getName());
		out.println("PRODUCT-TYPE" + "\t" + "P");
		String productID = cds.acypi;
		if (productID != null && productID.length() != 0) {
			out.println("PRODUCT-ID" + "\t" + productID);
		}
		ArrayList<String> geneComments = new ArrayList<String>();

		//print functions
		out.println("FUNCTION" + "\t" + cds.getName());
		for (String function : cds.getFunctions()) {
			out.println("FUNCTION" + "\t" + function);
		}
		Collection<KOAnnot> koAnnots = cds.kOAnnots;
		HashSet<String> ecs = new HashSet<String>();
		HashSet<String> kos = new HashSet<String>();
		ArrayList<String> dbLinks = new ArrayList<String>();
		for (KOAnnot koAnnot : koAnnots) {
			KO ko = koAnnot.ko;
			if (kos.add(ko.getId())) {
				if (ko.getDefinition() != null && ko.getDefinition().length() > 0) {
					out.println("FUNCTION" + "\t" + ko.getDefinition());
					out.println("FUNCTION-COMMENT" + "\t" + "Corresponding to KEGG Orthology:" + ko.getId()
						+ "  definition.");
				}
				for (EC ec : ko.getECs()) {
					ecs.add(ec.getId());
				}
				dbLinks.add("KO:" + ko.getId());
			}
			geneComments.add("KEGG Orthology:" + ko.getId() + " annotated by method " + koAnnot.method.getDescription());
		}

		for (String ec : ecs) {
			if (ec != null && ec.length() > 0 && !ec.contains("-")) {
				out.println("EC" + "\t\t" + ec);
			}
		}

		if (cds.xp != null && cds.xp.length() > 0) {
			out.println("SYNONYM" + "\t\t" + cds.xp);
			dbLinks.add("RefSeq:" + cds.xp);

		}
		if (cds.glean != null && cds.glean.length() > 0) {
			out.println("SYNONYM" + "\t\t" + cds.glean);
			//			dbLinks.add("GLEAN:" + cds.glean);

		}

		//print dblinks
		dbLinks.add("PhylomeDB:" + cds.acypi);
		String geneId = cds.getGeneId();
		if (geneId != null && geneId.trim().length() > 0) {
			dbLinks.add(geneId);
		}
		String locGene = cds.getLocGene();
		if (locGene != null && locGene.trim().length() > 0) {
			dbLinks.add("AphidBase:" + locGene);
		}
		for (String dbLink : dbLinks) {
			out.println("DBLINK" + "\t\t" + dbLink);
		}

		//print gene comments
		String geneComment = cds.getGeneComment();
		if (geneComment != null && geneComment.trim().length() > 0) {
			geneComments.add(geneComment);
		}
		boolean first = true;
		for (String comment : geneComments) {
			if (comment != null && comment.trim().length() > 0) {
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
}
