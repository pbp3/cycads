package org.cycads.entities.annotation;

import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public class SimpleSubseqAnnotation<AParent extends Annotation< ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends SimpleAnnotation<AParent, X, T, M> implements SubseqAnnotation<AParent, SS, X, T, M>
{

	//	EntityFactory<? extends X, ? extends M, ? extends T, ?> factory;

	SS	subsequence;
	M	method;

	public SimpleSubseqAnnotation(EntityFactory< ? extends X, ? extends M, ? extends T, ? , ? > factory,
			SS subsequence, M method) {
		super(factory, method);
		this.subsequence = subsequence;
	}

	@Override
	public SS getSubsequence() {
		return subsequence;
	}

	public void setSubsequence(SS subsequence) {
		this.subsequence = subsequence;
	}

}
