package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.DBRecord;
import org.cycads.entities.ExternalDatabase;

/**
 */
public class DBRecord implements DBRecord
{
	private ExternalDatabase				database;
	private String							accession;

	private Collection<DBToDBAnnotation>	dbAnnotations;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBRecord#getDbAnnotations()
	 */
	public Collection<DBToDBAnnotation> getDbAnnotations() {
		return dbAnnotations;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBRecord#getAccession()
	 */
	public String getAccession() {
		return accession;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IDBRecord#getDatabase()
	 */
	public ExternalDatabase getDatabase() {
		return database;
	}

}
