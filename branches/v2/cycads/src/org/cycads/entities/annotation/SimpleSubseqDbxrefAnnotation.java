package org.cycads.entities.annotation;

import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public class SimpleSubseqDbxrefAnnotation<AParent extends Annotation< ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends SimpleSubseqAnnotation<AParent, SS, X, T, M> implements SubseqDbxrefAnnotation<AParent, SS, X, T, M>
{

	private X	dbxref;

	public SimpleSubseqDbxrefAnnotation(EntityFactory< ? extends X, ? extends M, ? extends T, ? , ? > factory,
			SS subsequence, X dbxref, M method) {
		super(factory, subsequence, method);
		this.dbxref = dbxref;
	}

	@Override
	public X getDbxref() {
		return dbxref;
	}

}
