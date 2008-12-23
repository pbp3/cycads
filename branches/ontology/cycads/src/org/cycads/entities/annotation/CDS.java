/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.sequence.Subsequence;

public interface CDS<CDS_TYPE extends CDS< ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? >, M extends AnnotationMethod, R extends RNA< ? , ? , ? , ? , ? >>
		extends AnnotFeature<CDS_TYPE, SS, M>
{
	public Collection<R> getRNAsContains();

	public R getRNAParent();

	public void setRNAParent(R rna);

	public Collection<String> getFunctions();

	public void addFunction(String function);

}
