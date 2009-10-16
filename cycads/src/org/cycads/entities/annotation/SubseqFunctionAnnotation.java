/*
 * Created on 02/03/2009
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public interface SubseqFunctionAnnotation<AParent extends Annotation< ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends SubseqAnnotation<AParent, SS, X, T, M>, FunctionAnnotation<AParent, X, T, M>
{
}