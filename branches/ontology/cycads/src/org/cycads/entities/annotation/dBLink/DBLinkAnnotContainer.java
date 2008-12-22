/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;

// public interface DBLinkContainer<S extends DBLinkSource< ? , ? >, R extends DBRecord< ? >>
public interface DBLinkAnnotContainer<D extends OntologyAnnot< ? , S, R, M>, S extends DBLinkAnnotSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public void addDBLinkAnnot(D dBLink);

	public D getDBLinkAnnot(S source, M method, R target);

	public Collection<D> getDBLinkAnnots(M method, R target);

	public Collection<D> getDBLinkAnnots(M method, String dbName, String accession);

	public D getDBLinkAnnot(S source, String method, R target);

	public Collection<D> getDBLinkAnnots(String method, R target);

	public Collection<D> getDBLinkAnnots(String method, String dbName, String accession);

	public Collection<D> getDBLinkAnnots(DBLinkAnnotFilter<D> filter);

}
