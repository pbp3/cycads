/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.Collection;
import java.util.List;

public interface ObjectChainGetter
{
	public List<ObjectChain> getObjectChains(Collection<Object> objs, String definitionName);

	public List<ObjectChain> getObjectChains(Object obj, String definitionName);
}
