/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

public interface AnnotationClusterGetter {
	// public List<AnnotationCluster> getAnnotationClustera(
	// Collection<Object> objs, String definitionName);

	public List<AnnotationCluster> getAnnotationClusters(Object obj,
			String definitionName);
}
