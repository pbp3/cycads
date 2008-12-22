/*
 * Created on 21/12/2008
 */
package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;

public interface OntologyAnnotSource<OA extends OntologyAnnot< ? , ? , ? , ? >, O extends Ontology< ? , ? , ? >, M extends AnnotationMethod>
{
	public OA createOntologyAnnot(M method, O target);

	public OA createOntologyAnnot(M method, String accession, String dbName);

	public OA createOntologyAnnot(String method, O target);

	public OA createOntologyAnnot(String method, String accession, String dbName);

}
