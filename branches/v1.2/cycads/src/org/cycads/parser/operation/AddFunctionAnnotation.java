/*
 * Created on 15/09/2009
 */
package org.cycads.parser.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.EntityObject;
import org.cycads.entities.Function;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityFunctionFactory;
import org.cycads.entities.note.Type;

public class AddFunctionAnnotation<S extends EntityObject>
		extends SimpleRelationshipOperation<S, Annotation< ? , Function>>
{

	private final EntityFunctionFactory	factory;
	private final AnnotationMethod		method;
	private final Collection<Type>		annotationTypes;

	public AddFunctionAnnotation(Pattern tagNameRegex, Pattern tagValueRegex, AnnotationMethod method,
			EntityFunctionFactory factory, Collection<Type> annotationTypes) {
		super(tagNameRegex, tagValueRegex);
		this.factory = factory;
		this.method = method;
		this.annotationTypes = annotationTypes;
	}

	@Override
	protected Collection<Annotation< ? , Function>> execute(S source, Note note) {
		Function function = factory.getFunction(note.getValue());
		Collection<Annotation< ? , Function>> ret = new ArrayList<Annotation< ? , Function>>();
		ret.add(source.addAnnotation(function, method, null, annotationTypes));
		return ret;
	}
}
