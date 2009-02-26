/*
 * Created on 06/01/2009
 */
package beta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.cycads.dbExternal.EC;

public class TabFileGenerator
{
	PrintStream	out;

	public TabFileGenerator(PrintStream out) {
		this.out = out;
		out.println("ID\tRefSeqId\tRefSeqFunction\tKAAS1ID\tKAAS1EC\tKAAS2ID\tKAAS2EC\tKAAS3ID\tKAAS3EC");
	}

	public TabFileGenerator(File fileOut) throws FileNotFoundException {
		this(new PrintStream(new FileOutputStream(fileOut, false)));
	}

	public void write(CDSSQL cds) {
		printField(cds.getAcypi());
		printField(cds.xp);
		printField(cds.name);

		KOAnnot[] koAnnots = new KOAnnot[3];
		for (KOAnnot koAnnot : cds.kOAnnots) {
			koAnnots[koAnnot.getMethod().getId() - 1] = koAnnot;
		}

		for (KOAnnot koAnnot : koAnnots) {
			if (koAnnot != null) {
				printField(koAnnot.getKo().getId());
				String ecs = "";
				for (EC ec : koAnnot.getKo().getECs()) {
					if (ecs.length() > 0) {
						ecs += ";";
					}
					ecs += ec.getId();
				}
				printField(ecs);
			}
			else {
				printField(null);
				printField(null);
			}
		}

		out.println();
		out.flush();
	}

	public void printField(String val) {
		if (val == null || val.length() == 0) {
			val = "NA";
		}
		out.print(val);
		out.print("\t");
	}
}
