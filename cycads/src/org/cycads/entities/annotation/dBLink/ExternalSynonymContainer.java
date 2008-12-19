/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

// public interface DBLinkContainer<S extends DBLinkSource< ? , ? >, R extends DBRecord< ? >>
public interface ExternalSynonymContainer<D extends ExternalSynonym< ? , ? >, S extends DBLinkAnnotationSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public void addDBLink(D dBLink);

	public D getDBLink(S source, R target);

	public Collection<D> getDBLinks(R target);

	public Collection<D> getDBLinks(String dbName, String accession);

	public Collection<D> getDBLinks(String dbName);

	public Collection<D> getDBLinks(ExternalSynonymFilter<D> filter);

}
