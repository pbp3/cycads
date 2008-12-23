/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.sequence.Subsequence;

public interface Gene<GENE_TYPE extends Gene< ? , ? , ? , ? >, SS extends Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? >, M extends AnnotationMethod, R extends RNA< ? , ? , ? , ? , ? >>
		extends AnnotFeature<GENE_TYPE, SS, M>
{
	public Collection<R> getRNAProducts();

}
