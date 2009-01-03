/*
 * Created on 20/12/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface Ontology<OA extends AnnotOntology< ? , ? , ? , ? >, O extends Ontology< ? , ? , ? , ? >, M extends AnnotationMethod, DB extends ExternalDatabase< ? >>
		extends AnnotOntologySource<OA, O, M>, DBRecord<DB>
{
	public Collection<String> getFunctions();

	public void addFunction(String function);
}
