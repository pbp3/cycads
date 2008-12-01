/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

public interface DBLinkContainer
{
	public void addDBLink(DBLink<DBLinkSource, DBRecord> dBLink);

	public <S extends DBLinkSource, R extends DBRecord> DBLink<S, R> getDBLink(AnnotationMethod method, R record,
			S source);

	public <R extends DBRecord> Collection<DBLink<DBLinkSource, R>> getDBLinks(AnnotationMethod method, R record);

	public Collection<DBLink<DBLinkSource, DBRecord>> getDBLinks(AnnotationMethod method, String accession,
			String dbName);

	public Collection<DBLink<DBLinkSource, DBRecord>> getDBLinks(DBLinkFilter filter);

}
