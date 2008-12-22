/*
 * Created on 21/12/2008
 */
package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;

public interface OntologyAnnotationSource<OA extends OntologyAnnot, R extends DBRecord, M extends AnnotationMethod>
{
	public OA createOntologyAnnot(M method, Ontology target);

	public OA createOntologyAnnot(M method, String accession, String dbName);

	public OA createOntologyAnnot(String method, Ontology target);

	public OA createOntologyAnnot(String method, String accession, String dbName);

}
