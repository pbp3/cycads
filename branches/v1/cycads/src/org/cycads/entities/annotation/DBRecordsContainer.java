/*
 * Created on 21/12/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface DBRecordsContainer<R extends DBRecord< ? >>
{
	public Collection<R> getDBRecords();

	public Collection<R> getDBrecords(String database);

	public void addDBRecord(R dbRecord);

	public void addDBRecord(String database, String accession);
}
