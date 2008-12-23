package org.cycads.entities.annotation;

public interface AnnotOntology<ONTANNOT_TYPE extends AnnotOntology< ? , ? , ? , ? >, S extends AnnotOntologySource< ? , ? , ? >, O extends Ontology< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends Annotation<ONTANNOT_TYPE, S, M>
{
	public O getOntology();
}