/*
 * Created on 22/10/2008
 */
package org.cycads.general.biojava;

import org.cycads.general.CacheCleanerAbstract;

public class CacheCleanerBJ extends CacheCleanerAbstract
{

	public CacheCleanerBJ(int cacheMax) {
		super(cacheMax);
	}

	@Override
	public void clear() {
		BioJavaxSession.clearCache();
		resetCacheSize();
	}

}
