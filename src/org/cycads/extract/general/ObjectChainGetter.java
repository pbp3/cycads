/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.Collection;
import java.util.List;

public interface ObjectChainGetter
{
	public List<AnnotationCluster> getObjectChains(Collection<Object> objs, String definitionName);

	public List<AnnotationCluster> getObjectChains(Object obj, String definitionName);
}
