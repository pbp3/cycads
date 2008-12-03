/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import org.cycads.entities.annotation.dBLink.ExternalDatabase;
import org.cycads.general.biojava.BioSql;

public class ExternalDatabaseBJ implements ExternalDatabase<DBRecordBJ>
{
	static Hashtable<String, ExternalDatabaseBJ>	externalDBs	= new Hashtable<String, ExternalDatabaseBJ>();
	private Hashtable<String, DBRecordBJ>			records		= new Hashtable<String, DBRecordBJ>();
	private String									name;

	private ExternalDatabaseBJ(String name) {
		this.name = name;
	}

	@Override
	public String getDbName() {
		return name;
	}

	@Override
	public Collection<DBRecordBJ> getRecords() {
		Collection<String> records = BioSql.getAccessions(getDbName());
		ArrayList<DBRecordBJ> result = new ArrayList<DBRecordBJ>(records.size());
		for (String record : records) {
			result.add(getOrCreateDBRecord(record));
		}
		return result;
	}

	@Override
	public DBRecordBJ getOrCreateDBRecord(String accession) {
		DBRecordBJ record = records.get(accession);
		if (record == null) {
			record = new DBRecordBJ(this, accession);
			records.put(accession, record);
		}
		return record;
	}

	public static ExternalDatabaseBJ getOrCreateExternalDB(String externalDBName) {
		ExternalDatabaseBJ externalDB = externalDBs.get(externalDBName);
		if (externalDB == null) {
			externalDB = new ExternalDatabaseBJ(externalDBName);
			externalDBs.put(externalDBName, externalDB);
		}
		return externalDB;
	}

}
