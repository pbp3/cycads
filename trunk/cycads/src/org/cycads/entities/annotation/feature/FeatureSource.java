/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.feature;

import org.cycads.entities.annotation.AnnotationMethod;

public interface FeatureSource<F extends Feature< ? , ? , ? >, C extends CDS< ? , ? , ? , ? >, R extends RNA< ? , ? , ? , ? , ? >, G extends Gene< ? , ? , ? , ? >, M extends AnnotationMethod>
{
	public F createFeature(M method, String type);

	public C createCDS(M method);

	public R createRNA(M method, String type);

	public G createGene(M method);
}
