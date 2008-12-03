/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

// public interface DBLinkContainer<S extends DBLinkSource< ? , ? >, R extends DBRecord< ? >>
public interface DBLinkContainer<D extends DBLink<S, R, M>, S extends DBLinkSource< ? , ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public void addDBLink(D dBLink);

	public D getDBLink(M method, R record, S source);

	public Collection<D> getDBLinks(M method, R record);

	public Collection<D> getDBLinks(M method, String accession, String dbName);

	public Collection<D> getDBLinks(DBLinkFilter<D> filter);

}
