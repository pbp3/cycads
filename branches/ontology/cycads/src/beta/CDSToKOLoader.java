/*
 * Created on 21/10/2008
 */
package beta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class CDSToKOLoader extends FileLoaderLine
{
	int			method;
	Connection	con;
	Statement	stmt;

	public CDSToKOLoader(Progress progress, int method) {
		super(progress);
		this.method = method;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pisum", "root", "baobab");
			stmt = con.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadLine(String line) {
		if (!line.startsWith(ParametersDefault.cdsToKOLoaderComment())) {
			String[] sep = line.split(ParametersDefault.cdsToKOLoaderSeparator());
			ResultSet rs;
			try {
				if (sep.length > 0 && sep[0] != null & sep[0].trim().length() > 0) {
					rs = stmt.executeQuery("SELECT * FROM CDS WHERE ACYPI='" + sep[0] + "'");
					if (!rs.next()) {
						stmt.executeUpdate("INSERT INTO CDS(ACYPI) VALUES('" + sep[0] + "')");
					}
					if (sep.length == 2) {
						stmt.executeUpdate("INSERT INTO CDS_KO(ACYPI, KO_ACCESSION, METHOD_ID) VALUES('" + sep[0]
							+ "','" + sep[1] + "'," + method + ")");
					}
					progress.completeStep();
				}
			}
			catch (Exception e) {
				System.err.println(line + e.getMessage());
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public String getCDSName(String cdsPart) {
		return cdsPart.split(ParametersDefault.cdsToKOLoaderSeparatorCDSName())[ParametersDefault.cdsToKOLoaderPosCDSName()];
		// return cdsPart.split("\\|")[3];
	}
}
