/*
 * Created on 22/10/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Type;

public interface Associable
{
	public <TA extends EntityObject> Association< ? , TA> addAssociation(TA target, Collection<Type> associationTypes);

	//Paremters nulls will be not consider
	public <TA extends EntityObject> Collection< ? extends Association< ? , TA>> getAssociations(TA target,
			Collection<Type> associationTypes);
}
