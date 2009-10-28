/*
 * Created on 26/02/2009
 */
package org.cycads.entities.factory;

import org.cycads.entities.EntityFinder;
import org.cycads.entities.EntityObject;
import org.cycads.entities.Feature;
import org.cycads.entities.Function;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.AssociationFinder;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.synonym.Dbxref;

public interface EntityFactory<X extends Dbxref, M extends AnnotationMethod, T extends Type, O extends Organism< ? >, F extends Function, E extends EntityObject, A extends Annotation< ? , ? >, FE extends Feature>
		extends EntityDbxrefFactory<X>, EntityMethodFactory<M>, EntityTypeFactory<T>, EntityOrganismFactory<O>,
		EntityFunctionFactory<F>, AnnotationFinder<E>, AssociationFinder<E>, EntityAnnotationFactory<E>,
		EntityFinder<E>, EntityFeatureFactory<FE>
{
}
