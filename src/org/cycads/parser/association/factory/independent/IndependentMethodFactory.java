/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association.factory.independent;

import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.general.Config;

public class IndependentMethodFactory<M extends AnnotationMethod> implements IndependentObjectFactory<M>
{
	private EntityMethodFactory<M>	entityFactory;
	private List<Pattern>			methodPatterns;
	private List<String>			methodNames;
	private M						methodDefault;

	public IndependentMethodFactory(EntityMethodFactory<M> entityFactory, List<Pattern> methodPatterns,
			List<String> methodNames, M methodDefault) {
		this.entityFactory = entityFactory;
		this.methodPatterns = methodPatterns;
		this.methodNames = methodNames;
		this.methodDefault = methodDefault;
	}

	@Override
	public M create(String value) {
		if (methodPatterns == null || methodPatterns.isEmpty() || methodNames == null || methodNames.isEmpty()
			|| value == null) {
			return getMethod(value);
		}
		else {
			return getMethod(Config.transform(value, methodPatterns, methodNames));
		}
	}

	private M getMethod(String value) {
		if (value == null || value.isEmpty()) {
			return methodDefault;
		}
		else {
			return entityFactory.getAnnotationMethod(value);
		}
	}
}
