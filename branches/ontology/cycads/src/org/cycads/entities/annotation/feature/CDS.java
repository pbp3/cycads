/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.sequence.Sequence;

public interface CDS<CDS_TYPE extends CDS< ? , ? , ? , ? , ? >, L extends Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? >, SEQ extends Sequence< ? , ? , ? , ? , ? , ? >, M extends AnnotationMethod, R extends RNA< ? , ? , ? , ? , ? , ? >>
		extends Feature<CDS_TYPE, L, SEQ, M>
{
	public Collection<R> getRNAsContains();

	public R getRNAParent();

	public void setRNAParent(R rna);
}
