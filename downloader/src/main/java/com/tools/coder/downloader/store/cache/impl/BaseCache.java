package com.tools.coder.downloader.store.cache.impl;

import android.content.Context;
import android.os.Parcelable;

import com.tools.coder.downloader.store.cache.Cache;
import com.tools.coder.downloader.store.util.ByteUtils;

import java.io.Serializable;

/**
 * Created by Spring-Xu on 15/8/3.
 *
 * @author Spring-Xu
 */
public abstract class BaseCache {
    /**
     * cache
     */
    private MemoryCache mCache;

    /**
     * 支持加密持久化
     */
    private DiskCache mDiskCache;

    public BaseCache() {
        mCache = new MemoryCache();
    }

    /**
     * 加入磁盘缓存
     *
     * @param context
     * @param key      缓存key
     * @param bytes    缓存value
     * @param valueTag 缓存的对象标记，解析的时候需要
     */
    protected void save(Context context, String key, byte[] bytes, String valueTag) {
        DiskCache.DEntry entry = new DiskCache.DEntry();
        entry.data = bytes;
        entry.etag = valueTag;
        entry.lastModified = System.currentTimeMillis();
        entry.serverDate = System.currentTimeMillis();
        entry.softTtl = System.currentTimeMillis();
        save(context, key, entry);
    }

    /**
     * 惰性初始化 DiskCache
     *
     * @param context
     */
    private void initDiskCacheLazy(Context context) {
        if (mDiskCache != null) {
            return;
        }

        synchronized (BaseCache.class) {
            if (mDiskCache == null) {
                // 目前采用 Store类名作为文件名区分
                mDiskCache = new DiskCache(context, getClass().getName());
                mDiskCache.initialize();
            }
        }
    }

    /**
     * 加入磁盘缓存
     *
     * @param context
     * @param key     缓存key
     * @param entry   缓存value
     */
    protected void save(Context context, String key, DiskCache.DEntry entry) {
        initDiskCacheLazy(context);
        mDiskCache.put(key, entry);
    }

    /**
     * 从磁盘缓存中读取
     *
     * @param key 缓存key
     * @return 缓存value
     */
    protected DiskCache.DEntry load(Context context, String key) {
        initDiskCacheLazy(context);
        if (mDiskCache == null) {
            return new DiskCache.DEntry();
        }

        DiskCache.DEntry dentry = new DiskCache.DEntry();
        Cache.Entry entry = mDiskCache.get(key);

        if (entry != null) {
            dentry.data = entry.data;
            dentry.softTtl = entry.softTtl;
            dentry.etag = entry.etag;
            dentry.serverDate = entry.serverDate;
            dentry.lastModified = entry.lastModified;
            dentry.responseHeaders = entry.responseHeaders;
            dentry.ttl = entry.ttl;
        }
        return dentry;
    }

    /**
     * 加入缓存
     *
     * @param key      缓存key
     * @param value    缓存value
     * @param duration 缓存持续时间 {@link MemoryCache#ALWAYS_EXPIRE MemoryCache#NON_EXPIRE}
     */
    protected void put(String key, Object value, long duration) {
        mCache.put(key, value, duration);
    }

    /**
     * 加入缓存 默认缓存项每次都过期
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    protected void put(String key, Object value) {
        put(key, value, MemoryCache.ALWAYS_EXPIRE);
    }

    /**
     * 移除缓存项
     *
     * @param key
     */
    protected void remove(String key) {
        mCache.remove(key);
    }

    /**
     * 擦除磁盘缓存项
     *
     * @param key
     */
    protected void wipe(String key) {
        if (mDiskCache != null) {
            mDiskCache.remove(key);
        }
    }

    /**
     * 获取缓存项
     * <p>
     * 在获取缓存项建议对缓存是否过期进行判断{@link #isExpired(String)}
     *
     * @param key
     * @return
     */
    protected Object get(String key) {
        return mCache.get(key);
    }

        /**
         * 清除
         *
         * @param key
         */
    public void clearAll(String key) {
        remove(key);
        wipe(key);
    }

    /**
     * 缓存是否过期
     *
     * @param key
     * @return
     */
    protected boolean isExpired(String key) {
        return mCache.isExpired(key);
    }

}
