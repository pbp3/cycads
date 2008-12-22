/*
 * Created on 20/12/2008
 */
package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.AnnotationMethod;

public interface Ontology<OA extends OntologyAnnot< ? , ? , ? , ? >, O extends Ontology< ? , ? , ? >, M extends AnnotationMethod>
		extends DBRecord, OntologyAnnotSource<OA, O, M>
{
	public String[] getFunctions();
}
