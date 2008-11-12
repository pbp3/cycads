/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface ExternalDatabase
{

	/**
	 * Getter of the property <tt>records</tt>
	 * 
	 * @return Returns the records.
	 * 
	 */
	public Collection<DBRecord> getRecords();

	public String getDbName();

}