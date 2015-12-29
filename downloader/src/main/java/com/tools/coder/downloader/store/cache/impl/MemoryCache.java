package com.tools.coder.downloader.store.cache.impl;

import android.support.v4.util.LruCache;

/**
 * Cache objects in memory
 *
 * @author Spring-Xu
 */
class MemoryCache<T> {

    /**
     * never expire
     */
    public static final int NON_EXPIRE = -1;

    /**
     * expire always
     */
    public static final int ALWAYS_EXPIRE = -2;

    /**
     * max cache numbers
     */
    private static final int MAX_ENTRY_SIZE = 256;

    /**
     * cache
     */
    private LruCache<String, CacheItem> mCache;

    public MemoryCache() {
        mCache = new LruCache<String, CacheItem>(MAX_ENTRY_SIZE);
    }

    /**
     * Add to cache
     *
     * @param key
     * @param value
     * @param duration {@link #NON_EXPIRE}
     */
    public void put(String key, T value, long duration) {
        CacheItem item = new CacheItem(value, duration);
        synchronized (mCache) {
            mCache.put(key, item);
        }
    }

    /**
     * Remove from cache
     *
     * @param key
     */
    public void remove(String key) {
        synchronized (mCache) {
            mCache.remove(key);
        }
    }

    /**
     * Get cache object from memory cache
     *
     * @param key
     * @return
     */
    public T get(String key) {
        CacheItem item = getCacheItem(key);
        return item == null ? null : item.getValue();
    }

    /**
     * If the object is expired or not exist, return true, else return false
     *
     * @param key
     * @return
     */
    public boolean isExpired(String key) {
        CacheItem item = getCacheItem(key);

        //not exist, it is expired
        if (item == null) {
            return true;
        }

        return item.isExpired();
    }

    /**
     * Get cache Object
     *
     * @param key
     * @return
     */
    private CacheItem getCacheItem(String key) {
        synchronized (mCache) {
            return mCache.get(key);
        }
    }

    /**
     * Cache item
     */
    private class CacheItem {

        /**
         * Start cache time
         */
        private long mStartTimestamp;

        /**
         * the period of validity of cache item
         */
        private long mDuration;

        /**
         * Cache item Object
         */
        private T mValue;

        /**
         * create cache item and count down at the same time
         *
         * @param value cache value
         */
        public CacheItem(T value, long duration) {
            this.mValue = value;
            this.mDuration = duration;
            this.mStartTimestamp = System.currentTimeMillis();
        }

        /**
         * Get the cache object
         *
         * @return
         */
        public T getValue() {
            return mValue;
        }

        /**
         * Is the cache itme is expired
         *
         * @return
         */
        public boolean isExpired() {
            // never
            if (mDuration == NON_EXPIRE) {
                return false;
            }

            // always expired
            if (mDuration == ALWAYS_EXPIRE) {
                return true;
            }

            //get the duration from now
            return System.currentTimeMillis() - mStartTimestamp > mDuration;
        }
    }
}
