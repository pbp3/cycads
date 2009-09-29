package org.cycads.entities.annotation;

import org.cycads.entities.Function;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public class SimpleSubseqFunctionAnnotation<AParent extends Annotation< ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends SimpleSubseqAnnotation<AParent, SS, X, T, M> implements SubseqFunctionAnnotation<AParent, SS, X, T, M>
{

	Function	function;

	public SimpleSubseqFunctionAnnotation(EntityFactory< ? extends X, ? extends M, ? extends T, ? , ? > factory,
			Function function, SS subsequence, M method) {
		super(factory, subsequence, method);
		this.function = function;
	}

	@Override
	public Function getFunction() {
		return function;
	}

}
