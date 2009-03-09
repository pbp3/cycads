/*
 * Created on 02/03/2009
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.Function;

public interface SubseqFunctionAnnotation<SS extends Subsequence< ? , ? , ? , ? , ? , ? >, AParent extends Annotation< ? , ? , ? , ? >, X extends Dbxref< ? >, T extends Type, M extends AnnotationMethod>
		extends SubseqAnnotation<SS, AParent, X, T, M>
{
	public Function getFunction();
}