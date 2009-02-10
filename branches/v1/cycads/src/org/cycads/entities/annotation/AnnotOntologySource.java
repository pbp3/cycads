/*
 * Created on 21/12/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface AnnotOntologySource<OA extends AnnotOntology< ? , ? , ? , ? >, O extends Ontology< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public OA getOrCreateOntologyAnnot(M method, O target);

	public OA getOrCreateOntologyAnnot(M method, String accession, String dbName);

	public OA getOntologyAnnot(M method, O target);

	public OA getOntologyAnnot(M method, String accession, String dbName);

	public Collection<OA> getOntologyAnnots(O target);

	public Collection<OA> getOntologyAnnots(String dbName, String accession);

	public Collection<OA> getOntologyAnnots(String dbName);

	public Collection<OA> getOntologyAnnots(AnnotationFilter<OA> filter);

	public M getMethodInstance(String method);

}
