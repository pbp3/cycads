/*
 * Created on 26/02/2009
 */
package org.cycads.entities.factory;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.Function;

public interface EntityFactory<X extends Dbxref< ? , ? , ? , ? >, M extends AnnotationMethod, T extends Type, O extends Organism< ? , ? , ? , ? , ? , ? >, A extends Annotation< ? , ? , ? , ? >>
		extends EntityDbxrefFactory<X>, EntityMethodFactory<M>, EntityTypeFactory<T>, EntityOrganismFactory<O>
{
	public Function getFunction(String name, String description);

	public Collection<A> getAnnotations(Dbxref< ? , ? , ? , ? > dbxref);
}
