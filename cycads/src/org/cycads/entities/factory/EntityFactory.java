/*
 * Created on 26/02/2009
 */
package org.cycads.entities.factory;

import java.util.Collection;

import org.cycads.entities.Function;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.Dbxref;

public interface EntityFactory<X extends Dbxref, M extends AnnotationMethod, T extends Type, O extends Organism< ? , ? >, F extends Function, A extends Annotation< ? , ? >>
		extends EntityDbxrefFactory<X>, EntityMethodFactory<M>, EntityTypeFactory<T>, EntityOrganismFactory<O>,
		EntityFunctionFactory<F>
{
	public Collection<A> getAnnotations(X dbxref);
}
