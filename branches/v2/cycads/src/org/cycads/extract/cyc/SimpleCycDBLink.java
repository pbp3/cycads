/*
 * Created on 01/04/2009
 */
package org.cycads.extract.cyc;

public class SimpleCycDBLink implements CycDBLink
{
	String	dbName;
	String	accession;

	protected SimpleCycDBLink(String dbName, String accession) {
		this.dbName = dbName;
		this.accession = accession;
	}

	public String getDbName() {
		return dbName;
	}

	public String getAccession() {
		return accession;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CycDBLink)) {
			return false;
		}
		CycDBLink o = (CycDBLink) obj;
		return o.getAccession().equals(this.getAccession()) && o.getDbName().equals(this.getDbName());
	}

}
