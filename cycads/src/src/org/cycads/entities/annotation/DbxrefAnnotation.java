package org.cycads.entities.annotation;

import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public interface DbxrefAnnotation<AParent extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Annotation<AParent, X, T, M>
{
	public X getDbxref();
}
