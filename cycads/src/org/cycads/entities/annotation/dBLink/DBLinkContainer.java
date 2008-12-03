/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

// public interface DBLinkContainer<S extends DBLinkSource< ? , ? >, R extends DBRecord< ? >>
public interface DBLinkContainer<D extends DBLink<S, R>, S extends DBLinkSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? >>
{
	public void addDBLink(D dBLink);

	public D getDBLink(AnnotationMethod method, R record, S source);

	public Collection<D> getDBLinks(AnnotationMethod method, R record);

	public Collection<D> getDBLinks(AnnotationMethod method, String accession, String dbName);

	public Collection<D> getDBLinks(DBLinkFilter<D> filter);

}
