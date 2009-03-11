/*
 * Created on 27/02/2009
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public interface SubseqAnnotation<SS extends Subsequence< ? , ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod>
		extends Annotation<X, T, M>
{
	public SS getSubsequence();
}
