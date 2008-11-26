/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface DBLinkSource
{
	public DBLink getOrCreateDBLink(AnnotationMethod method, DBRecord record);

	public DBLink getOrCreateDBLink(AnnotationMethod method, String accession, String dbName);

	public Collection<DBLink> getDBLinks(DBLinkFilter filter);

	public void addDBLink(DBLink dBLink);

}
