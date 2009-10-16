/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityMethodFactory;

public class IndependentMethodFactory<M extends AnnotationMethod> implements IndependentObjectFactory<M>
{
	private EntityMethodFactory<M>	entityFactory;

	public IndependentMethodFactory(EntityMethodFactory<M> entityFactory) {
		this.entityFactory = entityFactory;
	}

	@Override
	public M create(String value) {
		if (value == null) {
			return null;
		}
		return entityFactory.getAnnotationMethod(value);
	}

}
