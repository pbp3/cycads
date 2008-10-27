/*
 * Created on 15/10/2008
 */
package org.cycads.general;

public abstract class CacheCleanerAbstract implements CacheCleaner
{
	int	cacheSize	= 0, cacheMax;

	public CacheCleanerAbstract(int cacheMax) {
		this.cacheMax = cacheMax;
	}

	/* (non-Javadoc)
	 * @see org.cycads.general.CacheCleaner#incCache()
	 */
	public void incCache() {
		if (++cacheSize >= cacheMax) {
			clear();
		}
	}

	/* (non-Javadoc)
	 * @see org.cycads.general.CacheCleaner#clear()
	 */
	public abstract void clear();

	protected void resetCacheSize() {
		cacheSize = 0;
	}

}
