/*
 * Created on 21/12/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface DBRecordsContainer<R extends DBRecord< ? >>
{
	public R getDBRecord(String database, String accession);

	public Collection<R> getDBrecords(String database);

	public void addDBrecord(R dbRecord);

	public void addDBrecord(String database, String accession);
}
