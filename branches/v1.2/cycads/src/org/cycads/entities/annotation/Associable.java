/*
 * Created on 22/10/2009
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.note.Type;

public interface Associable
{
	public <TA extends BasicEntity> Association< ? , TA> addAssociation(TA target, Collection<Type> associationTypes);

	//Paremters nulls will be not consider
	public <TA extends BasicEntity> Collection< ? extends Association< ? , TA>> getAssociations(TA target,
			Collection<Type> associationTypes);
}
