/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

public interface ExternalDatabase<R extends DBRecord< ? , ? , ? , ? >>
{
	public Collection<R> getRecords();

	public String getDbName();

	public R getOrCreateDBRecord(String accession);

}