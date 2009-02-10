/*
 * Created on 06/01/2009
 */
package beta;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import org.cycads.ui.Arguments;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class GeneratePFFile
{
	public static void main(String[] args) {
		File file = Arguments.getFileToSave(args, 0, "", "pf file to generate");
		Progress progress = new ProgressPrintInterval(System.out, 100, "Generating pf file " + file.getPath(),
			"Finished");
		progress.init();
		try {
			PFFileGenerator generator = new PFFileGenerator(file);
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pisum", "root", "baobab");
			Statement stmt = con.createStatement();
			Hashtable<Integer, Method> methods = new Hashtable<Integer, Method>();
			ResultSet rs = stmt.executeQuery("select * FROM Method");
			while (rs.next()) {
				int id = rs.getInt("Method_ID");
				String name = rs.getString("Name");
				methods.put(id, new Method(id, name));
			}
			rs = stmt.executeQuery("select * FROM CDS");
			ArrayList<CDSSQL> cdss = new ArrayList<CDSSQL>();
			while (rs.next()) {
				CDSSQL cds = new CDSSQL(rs.getString("ACYPI"), rs.getString("XP"), rs.getString("GLEAN"));
				cds.setName(rs.getString("NAME"));
				cds.setGeneId(rs.getString("DBXREF_GENEID"));
				cds.setLocGene(rs.getString("LOC_GENE"));
				cds.setGeneComment(rs.getString("GENE_COMMENT"));
				cdss.add(cds);
			}
			Hashtable<String, KOSQL> kos = new Hashtable<String, KOSQL>();
			for (CDSSQL cds : cdss) {
				rs = stmt.executeQuery("select * FROM CDS_KO where ACYPI='" + cds.acypi + "'");
				while (rs.next()) {
					int methId = rs.getInt("METHOD_ID");
					Method method = methods.get(methId);
					String koId = rs.getString("KO_ACCESSION");
					KOSQL ko = kos.get(koId);
					if (ko == null) {
						ko = loadKO(koId, con);
					}
					if (ko != null) {
						cds.addKOAnnot(new KOAnnot(ko, method));
					}
				}
				generator.write(cds);
				progress.completeStep();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		progress.finish();
	}

	private static KOSQL loadKO(String koId, Connection con) throws SQLException {
		KOSQL ko = null;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * FROM KO where Accession='" + koId + "'");
		if (rs.next()) {
			ko = new KOSQL(koId, rs.getString("Function"));
			rs = stmt.executeQuery("select * FROM KO_EC where KO_ACCESSION='" + koId + "'");
			while (rs.next()) {
				ko.ecs.add(new ECSQL(rs.getString("EC_ACCESSION")));
			}

		}
		return ko;
	}
}
