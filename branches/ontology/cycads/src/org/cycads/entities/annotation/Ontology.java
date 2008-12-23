/*
 * Created on 20/12/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface Ontology<OA extends AnnotOntology< ? , ? , ? , ? >, O extends Ontology< ? , ? , ? , ? >, M extends AnnotationMethod, R extends DBRecord< ? >>
		extends AnnotOntologySource<OA, O, M>
{
	public Collection<String> getFunctions();

	public void addFunction(String function);

	public R getDBRecord();
}
