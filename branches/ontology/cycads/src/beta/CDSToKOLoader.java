/*
 * Created on 21/10/2008
 */
package beta;

import java.sql.Connection;
import java.sql.DriverManager;
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
			if (sep.length == 2) {
				try {
					stmt.executeUpdate("INSERT INTO CDS_KO(ACYPI, KO_ACCESSION, METHOD_ID) VALUES('" + sep[0] + "','"
						+ sep[1] + "'," + method + ")");
					progress.completeStep();
				}
				catch (Exception e) {
					System.err.println(line + e.getMessage());
					System.exit(0);
				}
			}
		}
	}

	public String getCDSName(String cdsPart) {
		return cdsPart.split(ParametersDefault.cdsToKOLoaderSeparatorCDSName())[ParametersDefault.cdsToKOLoaderPosCDSName()];
		// return cdsPart.split("\\|")[3];
	}
}
