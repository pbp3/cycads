/*
 * Created on 26/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationMethod;

public interface OntologyAnnotContainer<OA extends OntologyAnnot< ? , ? , ? , ? >, S extends OntologyAnnotSource< ? , ? , ? >, O extends Ontology< ? , ? , ? >, M extends AnnotationMethod>
{
	public void addOntologyAnnot(OA ontologyAnnot);

	public OA getOntologyAnnot(S source, M method, O target);

	public Collection<OA> getOntologyAnnots(O target);

	public Collection<OA> getOntologyAnnots(String dbName, String accession);

	public Collection<OA> getOntologyAnnots(String dbName);

	public Collection<OA> getOntologyAnnots(AnnotationFilter<OA> filter);

}
