/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface AssociationFinder
{
	/* The arguments nulls are not considerated */
	public <SO extends EntityObject, TA extends EntityObject> Collection< ? extends Association< ? extends SO, ? extends TA>> getAssociations(
			SO source, TA target, Collection<Type> types, Dbxref synonym, AssociationFilter filter);

	/* The arguments nulls are not considerated */
	public <TA extends EntityObject> Collection< ? extends Association< ? , ? extends TA>> getAssociations(
			Type sourceType, TA target, Collection<Type> types, Dbxref synonym, AssociationFilter filter);

	/* The arguments nulls are not considerated */
	public <SO extends EntityObject> Collection< ? extends Association< ? extends SO, ? >> getAssociations(SO source,
			Type targetType, Collection<Type> types, Dbxref synonym, AssociationFilter filter);

	/* The arguments nulls are not considerated */
	public Collection< ? extends Association< ? , ? >> getAssociations(Type sourceType, Type targetType,
			Collection<Type> types, Dbxref synonym, AssociationFilter filter);

}
