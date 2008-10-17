/*
 * Created on 15/10/2008
 */
package org.cycads.general.biojava;


public class CacheCleaner
{
	int	cacheSize	= 0, cacheMax;

	public CacheCleaner(int cacheMax) {
		this.cacheMax = cacheMax;
	}

	public void incCache() {
		if (++cacheSize >= cacheMax) {
			clear();
		}
	}

	public void clear() {
		BioJavaxSession.clearCache();
		cacheSize = 0;
	}

}
