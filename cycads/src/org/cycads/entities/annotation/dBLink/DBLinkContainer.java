/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

//public interface DBLinkContainer<S extends DBLinkSource< ? , ? >, R extends DBRecord< ? >>
public interface DBLinkContainer<D extends DBLink<S, R>, S extends DBLinkSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? >>
{
	public void addDBLink(DBLink<S, R> dBLink);

	public DBLink<S, R> getDBLink(AnnotationMethod method, R record, S source);

	public Collection<DBLink<S, R>> getDBLinks(AnnotationMethod method, R record);

	public Collection<DBLink<S, R>> getDBLinks(AnnotationMethod method, String accession, String dbName);

	public Collection<DBLink<S, R>> getDBLinks(DBLinkFilter<D> filter);

}
