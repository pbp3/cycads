/*
 * Created on 10/07/2009
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Type;
import org.cycads.entities.reaction.Pathway;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.Dbxref;

public interface PathwayOrganismAnnotation<AParent extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod, P extends Pathway< ? , ? >, O extends Organism< ? , ? , ? , ? , ? , ? >>
		extends Annotation<AParent, X, T, M>
{

	public P getPathway();

	public O getOrganism();

}
