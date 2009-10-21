/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Type;

public interface AssociationFinder<E extends EntityObject>
{
	/* The arguments nulls are not considerated */
	public <SO extends E, TA extends E> Collection< ? extends Association< ? extends SO, ? extends TA>> getAssociations(
			SO source, TA target, AssociationFilter filter, Type... types);

	/* The arguments nulls are not considerated */
	public <TA extends E> Collection< ? extends Association< ? , ? extends TA>> getAssociations(Type sourceType,
			TA target, AssociationFilter filter, Type... types);

	/* The arguments nulls are not considerated */
	public <SO extends E> Collection< ? extends Association< ? extends SO, ? >> getAssociations(SO source,
			Type targetType, AssociationFilter filter, Type... types);

	/* The arguments nulls are not considerated */
	public Collection< ? extends Association< ? , ? >> getAssociations(Type sourceType, Type targetType,
			AssociationFilter filter, Type... types);

}
