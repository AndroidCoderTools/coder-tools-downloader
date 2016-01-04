package com.tools.coder.download.cache.impl;

import android.content.Context;

import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;
import com.tools.coder.download.cache.Cache;

import java.io.File;
import java.io.IOException;

/**
 * Created by Spring-Xu
 * Save at disk and encryption by {@link Crypto}
 *
 * @author Spring-Xu
 */
class DiskCache implements Cache {

    /**
     * Default maximum disk usage in bytes.
     */
    private static final int DEFAULT_DISK_USAGE_BYTES = 2 * 1024 * 1024;

    private DiskBasedCache mCache;

    private Crypto mCrypto;

    private File mCacheDir;

    private boolean isLazyInit;

    public DiskCache(Context context, String category) {
        mCrypto = new Crypto(
                new SharedPrefsBackedKeyChain(context),
                new SystemNativeCryptoLibrary());

        mCacheDir = new File(context.getFilesDir().getAbsolutePath(), category);
        if (mCacheDir.exists()) {
            isLazyInit = true;
            mCache = new DiskBasedCache(mCacheDir, DEFAULT_DISK_USAGE_BYTES);
            initialize();
        }
    }

    private void doLazyInit() {
        isLazyInit = true;
        mCacheDir.mkdirs();
        mCache = new DiskBasedCache(mCacheDir, DEFAULT_DISK_USAGE_BYTES);
        initialize();
    }

    private void lazyInit() {
        if (!isLazyInit) {
            doLazyInit();
        }
    }

    @Override
    public Entry get(String key) {
        lazyInit();
        if (key == null) {
            return null;
        }
        if (!mCrypto.isAvailable()) {
            return null;
        }
        Entry entry = mCache.get(key);
        if (entry != null) {
            try {
                entry.data = mCrypto.decrypt(entry.data, new Entity(""));
                return entry;
            } catch (KeyChainException e) {
                e.printStackTrace();
            } catch (CryptoInitializationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void put(String key, Entry entry) {
        lazyInit();

        if (key == null || entry == null) {
            return;
        }

        if (!mCrypto.isAvailable()) {
            return;
        }

        try {
            entry.data = mCrypto.encrypt(entry.data, new Entity(""));
            mCache.put(key, entry);
        } catch (KeyChainException e) {
            e.printStackTrace();
        } catch (CryptoInitializationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        lazyInit();
        mCache.initialize();
    }

    @Override
    public void invalidate(String key, boolean fullExpire) {
        lazyInit();
        mCache.invalidate(key, fullExpire);
    }

    @Override
    public void remove(String key) {
        lazyInit();
        mCache.remove(key);
    }

    @Override
    public void clear() {
        lazyInit();
        mCache.clear();
    }


    public static class DEntry extends Entry {
        @Override
        public boolean isExpired() {
            return false;
        }

        @Override
        public boolean refreshNeeded() {
            return false;
        }
    }
}
