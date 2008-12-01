/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.dBLink;

public interface DBRecord extends DBLinkSource, DBLinkContainer
{
	public String getAccession();

	public ExternalDatabase getDatabase();

}