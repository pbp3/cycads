/*
 * Created on 17/12/2008
 */
package org.cycads.loaders.gff3;

public class InvalidSequence extends Exception
{

	protected InvalidSequence(String db, String accession, int version) {
		super("Database:" + db + "; Accession:" + accession + "; Version: " + version);
	}

	protected InvalidSequence(String db, String accession) {
		super("Database:" + db + "; Accession:" + accession);
	}

}
