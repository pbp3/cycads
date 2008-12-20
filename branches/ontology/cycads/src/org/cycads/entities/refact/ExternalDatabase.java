package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.annotation.dBLink.ExternalDatabase;

public class ExternalDatabase implements ExternalDatabase
{
	String							dbName;

	private Collection<DBRecord>	records;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IExternalDatabase#getRecords()
	 */
	public Collection<DBRecord> getRecords()
	{
		return records;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IExternalDatabase#getDbName()
	 */
	public String getDbName()
	{
		return dbName;
	}

}
