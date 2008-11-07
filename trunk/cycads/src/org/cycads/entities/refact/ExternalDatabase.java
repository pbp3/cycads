package org.cycads.entities.refact;

import java.util.Collection;

public class ExternalDatabase extends Term
{

	private Collection<DBRecord>	records;

	/**
	 * Getter of the property <tt>records</tt>
	 * 
	 * @return Returns the records.
	 * 
	 */
	public Collection<DBRecord> getRecords()
	{
		return records;
	}

}
