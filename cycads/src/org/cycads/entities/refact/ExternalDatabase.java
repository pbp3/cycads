package org.cycads.entities.refact;

import java.util.Collection;
import java.util.Iterator;/*
 * Created on 07/11/2008
 */

public class ExternalDatabase extends Term
{

	/**
	 * 
	 */
	private Collection<DBRecord>	records;

	/**
	 * Getter of the property <tt>records</tt>
	 * 
	 * @return Returns the records.
	 * 
	 */
	public Collection<DBRecord> getRecords() {
		return records;
	}

}
