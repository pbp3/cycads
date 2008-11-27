/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface DBLinkCollection
{
	public void addDBLink(DBLink dBLink);

	public DBLink getDBLink(AnnotationMethod method, DBRecord record, DBLinkSource source);

	public Collection<DBLink> getDBLinks(AnnotationMethod method, DBRecord record);

	public Collection<DBLink> getDBLinks(AnnotationMethod method, String accession, String dbName);

	public Collection<DBLink> getDBLinks(DBLinkFilter filter);

}
