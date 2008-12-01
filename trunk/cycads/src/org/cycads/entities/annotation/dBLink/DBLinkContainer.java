/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

public interface DBLinkContainer
{
	public <S extends DBLinkSource<S>> void addDBLink(DBLink<S, DBRecord> dBLink);

	public <S extends DBLinkSource<S>, R extends DBRecord> DBLink<S, R> getDBLink(AnnotationMethod method, R record,
			S source);

	public <S extends DBLinkSource<S>, R extends DBRecord> Collection<DBLink<S, R>> getDBLinks(AnnotationMethod method,
			R record);

	public <S extends DBLinkSource<S>> Collection<DBLink<S, DBRecord>> getDBLinks(AnnotationMethod method,
			String accession, String dbName);

	public <S extends DBLinkSource<S>> Collection<DBLink<S, DBRecord>> getDBLinks(DBLinkFilter filter);

}
