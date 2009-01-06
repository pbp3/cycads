/*
 * Created on 06/01/2009
 */
package beta;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.cycads.dbExternal.EC;
import org.cycads.dbExternal.KO;

public class PFFileGenerator
{
	PrintStream	out;

	protected PFFileGenerator(PrintStream out) {
		this.out = out;
	}

	public void write(CDSSQL cds) {

		out.println("ID" + "\t\t" + cds.acypi);
		out.println("NAME" + "\t\t" + cds.acypi);
		out.println("PRODUCT-TYPE" + "\t" + "P");
		String productID = cds.acypi;
		if (productID != null && productID.length() != 0) {
			out.println("PRODUCT-ID" + "\t" + productID);
		}

		Collection<KOAnnot> koAnnots = cds.kOAnnots;
		HashSet<String> ecs = new HashSet<String>();
		ArrayList<String> dbLinks = new ArrayList<String>();
		for (KOAnnot koAnnot : koAnnots) {
			KO ko = koAnnot.ko;
			if (ko.getDefinition() != null && ko.getDefinition().length() > 0) {
				out.println("FUNCTION" + "\t" + ko.getDefinition());
				out.println("FUNCTION-COMMENT" + "\t" + "Description of KO " + ko.getId() + " annotated by "
					+ koAnnot.method.description);
			}
			for (EC ec : ko.getECs()) {
				ecs.add(ec.getId());
			}
			dbLinks.add("KO:" + ko.getId());
		}

		for (String ec : ecs) {
			if (ec != null && ec.length() > 0) {
				out.println("EC" + "\t\t" + ec);
			}
		}

		if (cds.xp != null && cds.xp.length() > 0) {
			out.println("SYNONYM" + "\t\t" + cds.xp);
			dbLinks.add("RefSeq:" + cds.xp);

		}
		if (cds.glean != null && cds.glean.length() > 0) {
			out.println("SYNONYM" + "\t\t" + cds.glean);
			dbLinks.add("GLEAN:" + cds.glean);

		}

		dbLinks.add("RefSeq:" + cds.acypi);
		for (String dbLink : dbLinks) {
			out.println("DBLINK" + "\t\t" + dbLink);
		}

	}
}
