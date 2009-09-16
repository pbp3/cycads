/*
 * Created on 15/09/2009
 */
package org.cycads.parser.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.EntityFactory;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.FunctionAnnotable;
import org.cycads.entities.annotation.FunctionAnnotation;
import org.cycads.entities.synonym.Function;

public class AddFunctionAnnotation<S extends FunctionAnnotable>
		extends SimpleRelationshipOperation<S, FunctionAnnotation>
{

	private EntityFactory		factory;
	private AnnotationMethod	method;

	protected AddFunctionAnnotation(Pattern tagNameRegex, Pattern tagValueRegex, String methodName,
			EntityFactory factory) {
		super(tagNameRegex, tagValueRegex);
		this.factory = factory;
		this.method = factory.getAnnotationMethod(methodName);
	}

	@Override
	protected Collection<FunctionAnnotation> execute(S source, Note note) {
		Function function = factory.getFunction(note.getValue(), null);
		Collection<FunctionAnnotation> ret = new ArrayList<FunctionAnnotation>();
		ret.add(source.addFunctionAnnotation(method, function));
		return ret;
	}
}
