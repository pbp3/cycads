/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

// public interface DBLinkContainer<S extends DBLinkSource< ? , ? >, R extends DBRecord< ? >>
public interface DBLinkAnnotationContainer<D extends DBLinkAnnotation< ? , S, R, M>, S extends DBLinkAnnotationSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public void addDBLink(D dBLink);

	public D getDBLink(S source, M method, R target);

	public Collection<D> getDBLinks(M method, R target);

	public Collection<D> getDBLinks(M method, String dbName, String accession);

	public D getDBLink(S source, String method, R target);

	public Collection<D> getDBLinks(String method, R target);

	public Collection<D> getDBLinks(String method, String dbName, String accession);

	public Collection<D> getDBLinks(DBLinkAnnotationFilter<D> filter);

}
