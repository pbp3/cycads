package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;

public interface OntologyAnnot<ONTANNOT_TYPE extends OntologyAnnot< ? , ? , ? , ? >, S extends OntologyAnnotSource< ? , ? , ? >, O extends Ontology< ? , ? , ? >, M extends AnnotationMethod>
		extends Annotation<ONTANNOT_TYPE, S, M>
{
	public O getOntology();
}