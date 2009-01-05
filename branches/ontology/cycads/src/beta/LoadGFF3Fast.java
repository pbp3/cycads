/*
 * Created on 05/01/2009
 */
package beta;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import org.cycads.general.Config;
import org.cycads.loaders.gff3.GFF3DocumentHandler;
import org.cycads.loaders.gff3.GFF3Parser;
import org.cycads.loaders.gff3.GFF3Record;
import org.cycads.loaders.gff3.InvalidSequence;
import org.cycads.ui.Arguments;

public class LoadGFF3Fast implements GFF3DocumentHandler
{

	Pattern		cdsPattern	= Pattern.compile(Config.gff3CDSTagExpression());
	Connection	con;
	int			i			= 0;

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
			if (cdsPattern.matcher(record.getType()).matches()) {
				//Get a Statement object
				stmt = con.createStatement();
				String acypi = (String) record.getNotesValues("ID").iterator().next();
				String name = (String) record.getNotesValues("Name").iterator().next();
				if (record.getSource().equals("GLEAN")) {
					stmt.executeUpdate("INSERT INTO CDS(ACYPI, GLEAN) VALUES('" + acypi + "','" + name + "')");
				}
				else {
					stmt.executeUpdate("INSERT INTO CDS(ACYPI, XP) VALUES('" + acypi + "','" + name + "')");
				}
				System.out.println(++i);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
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
