/*
 * Created on 05/01/2009
 */
package beta;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.cycads.general.Config;
import org.cycads.loaders.gff3.GFF3DocumentHandler;
import org.cycads.loaders.gff3.GFF3Parser;
import org.cycads.loaders.gff3.GFF3Record;
import org.cycads.loaders.gff3.InvalidSequence;
import org.cycads.ui.Arguments;

public class LoadGFF3Fast implements GFF3DocumentHandler
{

	Pattern							cdsPattern	= Pattern.compile(Config.gff3CDSTagExpression());
	Pattern							mRNAPattern	= Pattern.compile(Config.gff3MRNATagExpression());
	Pattern							genePattern	= Pattern.compile(Config.gff3GeneTagExpression());
	Connection						con;
	int								i			= 0;
	Hashtable<String, GFF3Record>	mrnas;
	Hashtable<String, GFF3Record>	genes;

	public static void main(String[] args) {
		Arguments arguments = Arguments.getInstanceDefault();
		File file = arguments.getFileToOpen(args, 0, "/home/avellozo/Pisum/ACYPI.gff3", "File GFF3");
		if (file == null) {
			return;
		}
		try {

			//Register the JDBC driver for MySQL.
			Class.forName("com.mysql.jdbc.Driver");

			(new GFF3Parser()).parse(file, new LoadGFF3Fast(), null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	LoadGFF3Fast() {
	}

	@Override
	public void commentLine(String comment) {

	}

	@Override
	public void startDocument(String locator) {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pisum", "root", "baobab");
			mrnas = new Hashtable<String, GFF3Record>();
			genes = new Hashtable<String, GFF3Record>();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void recordLine(GFF3Record record) throws InvalidSequence {
		Statement stmt;
		try {
			if (genePattern.matcher(record.getType()).matches()) {
				genes.put((String) record.getNotesValues("ID").iterator().next(), record);
			}
			else if (mRNAPattern.matcher(record.getType()).matches()) {
				mrnas.put((String) record.getNotesValues("ID").iterator().next(), record);
			}
			else if (cdsPattern.matcher(record.getType()).matches()) {
				//Get a Statement object
				stmt = con.createStatement();
				String acypi = (String) record.getNotesValues("ID").iterator().next();
				String extDB = (String) record.getNotesValues("Name").iterator().next();
				String mrnaID = (String) record.getNotesValues("Derives_from").iterator().next();
				GFF3Record mRNA = mrnas.get(mrnaID);
				String name = "", geneComment = "";
				String dbXRefGeneId = "", locGene = "";
				if (mRNA != null) {
					Collection<String> products = mRNA.getNotesValues("product");
					if (products.size() > 0) {
						name = products.iterator().next();
					}
					String geneId = (String) mRNA.getNotesValues("Parent").iterator().next();
					GFF3Record gene = genes.get(geneId);
					if (gene != null) {
						Collection<String> dbXRefGeneIds = gene.getNotesValues("db_xref");
						if (dbXRefGeneIds.size() > 0) {
							dbXRefGeneId = dbXRefGeneIds.iterator().next();
						}
						Collection<String> locGenes = gene.getNotesValues("Name");
						if (locGenes.size() > 0) {
							locGene = locGenes.iterator().next();
						}
						Collection<String> geneComments = gene.getNotesValues("note");
						if (geneComments.size() > 0) {
							geneComment = geneComments.iterator().next();
						}
					}

				}
				if (record.getSource().equals("GLEAN")) {
					stmt.executeUpdate("INSERT INTO CDS(ACYPI, NAME, GLEAN, DBXREF_GENEID, LOC_GENE, GENE_COMMENT) VALUES('"
						+ acypi
						+ "','"
						+ name
						+ "','"
						+ extDB
						+ "','"
						+ dbXRefGeneId
						+ "','"
						+ locGene
						+ "','"
						+ geneComment + "')");
				}
				else {
					stmt.executeUpdate("INSERT INTO CDS(ACYPI, NAME, XP,DBXREF_GENEID,LOC_GENE,GENE_COMMENT) VALUES('"
						+ acypi + "','" + name + "','" + extDB + "','" + dbXRefGeneId + "','" + locGene + "','"
						+ geneComment + "')");
				}
				System.out.println(++i);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(record.getSequenceID());
			System.exit(0);
		}
	}

	@Override
	public void endDocument() {
		try {
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
