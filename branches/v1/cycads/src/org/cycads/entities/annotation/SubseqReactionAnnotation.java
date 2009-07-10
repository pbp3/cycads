/*
 * Created on 10/07/2009
 */
package org.cycads.entities.annotation;

import org.cycads.entities.note.Type;
import org.cycads.entities.reaction.Reaction;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public interface SubseqReactionAnnotation<AParent extends Annotation< ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? >, X extends Dbxref< ? , ? , ? , ? >, T extends Type, M extends AnnotationMethod, R extends Reaction< ? , ? , ? >>
		extends SubseqAnnotation<AParent, SS, X, T, M>
{
	public R getReaction();

}
