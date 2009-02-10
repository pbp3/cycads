/*
 * Created on 15/10/2008
 */
package org.cycads.general;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleCacheCleanerController implements CacheCleanerController
{
	int									cacheSize	= 0, cacheMax;
	Collection<CacheCleanerListener>	cacheListeners;

	public SimpleCacheCleanerController(int cacheMax) {
		this(cacheMax, new ArrayList<CacheCleanerListener>());
	}

	public SimpleCacheCleanerController(int cacheMax, Collection<CacheCleanerListener> cacheListeners) {
		this.cacheMax = cacheMax;
		this.cacheListeners = cacheListeners;
	}

	public SimpleCacheCleanerController(int cacheMax, CacheCleanerListener cacheCleanerListener) {
		this(cacheMax);
		addCacheListener(cacheCleanerListener);
	}

	public SimpleCacheCleanerController(int cacheMax, CacheCleanerListener[] cacheCleanerListeners) {
		this(cacheMax);
		for (CacheCleanerListener cacheCleanerListener : cacheCleanerListeners) {
			addCacheListener(cacheCleanerListener);
		}
	}

	public void addCacheListener(CacheCleanerListener cacheCleanerListener) {
		if (cacheCleanerListener != null) {
			cacheListeners.add(cacheCleanerListener);
		}
	}

	public void removeCacheListener(CacheCleanerListener cacheCleanerListener) {
		cacheListeners.remove(cacheCleanerListener);
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
	public void clear() {
		resetCacheSize();
		for (CacheCleanerListener cacheCleanerListener : cacheListeners) {
			cacheCleanerListener.clearCache();
		}
	}

	protected void resetCacheSize() {
		cacheSize = 0;
	}

}
