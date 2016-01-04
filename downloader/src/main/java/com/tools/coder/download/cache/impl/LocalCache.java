package com.tools.coder.download.cache.impl;

import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.tools.coder.download.cache.util.ByteUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Cache data in memory and save the encrypt data on disk
 * Created by Spring-Xu on 15/12/29.
 */
public class LocalCache extends BaseCache {

    protected static final String TYPE_STRING = "String";
    protected static final String TYPE_INT = "int";
    protected static final String TYPE_LONG = "long";
    protected static final String TYPE_CHAR = "char";
    protected static final String TYPE_DOUBLE = "double";
    protected static final String TYPE_FLOAT = "float";
    protected static final String TYPE_SHORT = "short";

    /**
     * 缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void putAndSave(Context context, String key, Parcelable value) {
        if (key != null && value != null) {
            put(key, value);
            save(context, key, ByteUtils.getBytes(value), value.getClass().getName());
        }
    }

    /**
     * 有效获取缓存项
     *
     * @param key
     * @return
     */
    protected <T extends Object> T getParcelable(Context context, String key, Parcelable.Creator<T> creator) {
        Object value = get(key);
        if (value != null) {
            return (T) value;
        }
        DiskCache.DEntry entry = load(context, key);
        try {
            if (entry != null && entry.data != null) {
                value = ByteUtils.getParcelable(entry.data, creator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) value;
    }

    /**
     * 有效获取缓存项
     *
     * @param key
     * @return
     */
    protected long getLong(Context context, String key) {
        Object value = get(key);
        if (value != null) {
            return (long) value;
        }
        DiskCache.DEntry entry = load(context, key);
        if (entry != null && entry.data != null) {
            value = ByteUtils.getLong(entry.data);
        }

        return (long) value;
    }

    /**
     * 缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void putAndSave(Context context, String key, long value) {
        if (key != null) {
            put(key, value);
            save(context, key, ByteUtils.getBytes(value), TYPE_LONG);
        }
    }

    /**
     * 有效获取缓存项
     *
     * @param key
     * @return
     */
    protected short getShort(Context context, String key) {
        Object value = get(key);
        if (value != null) {
            return (short) value;
        }
        DiskCache.DEntry entry = load(context, key);
        if (entry != null && entry.data != null) {
            value = ByteUtils.getShort(entry.data);
        }

        return (short) value;
    }

    /**
     * 缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void putAndSave(Context context, String key, short value) {
        if (key != null) {
            put(key, value);
            save(context, key, ByteUtils.getBytes(value), TYPE_SHORT);
        }
    }

    /**
     * 有效获取缓存项
     *
     * @param key
     * @return
     */
    protected char getChar(Context context, String key) {
        Object value = get(key);
        if (value != null) {
            return (char) value;
        }
        DiskCache.DEntry entry = load(context, key);
        if (entry != null && entry.data != null) {
            value = ByteUtils.getChar(entry.data);
        }

        return (char) value;
    }

    /**
     * 缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void putAndSave(Context context, String key, char value) {
        if (key != null) {
            put(key, value);
            save(context, key, ByteUtils.getBytes(value), TYPE_CHAR);
        }
    }

    /**
     * 有效获取缓存项
     *
     * @param key
     * @return
     */
    protected double getDouble(Context context, String key) {
        Object value = get(key);
        if (value != null) {
            return (double) value;
        }
        DiskCache.DEntry entry = load(context, key);
        if (entry != null && entry.data != null) {
            value = ByteUtils.getDouble(entry.data);
        }

        return (double) value;
    }

    /**
     * 缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void putAndSave(Context context, String key, double value) {
        if (key != null) {
            put(key, value);
            save(context, key, ByteUtils.getBytes(value), TYPE_DOUBLE);
        }
    }

    /**
     * 缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void putAndSave(Context context, String key, int value) {
        if (key != null) {
            put(key, value);
            save(context, key, ByteUtils.getBytes(value), TYPE_INT);
        }
    }

    /**
     * 有效获取缓存项
     *
     * @param key
     * @return
     */
    protected int getInteger(Context context, String key) {
        Object value = get(key);
        if (value != null) {
            return (int) value;
        }
        DiskCache.DEntry entry = load(context, key);
        if (entry != null && entry.data != null) {
            value = ByteUtils.getInt(entry.data);
        }

        return value == null ? 0 : (int) value;
    }

    /**
     * 缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void putAndSave(Context context, String key, float value) {
        if (key != null) {
            put(key, value);
            save(context, key, ByteUtils.getBytes(value), TYPE_FLOAT);
        }
    }

    /**
     * 有效获取缓存项
     *
     * @param key
     * @return
     */
    protected float getFloat(Context context, String key) {
        Object value = get(key);
        if (value != null) {
            return (float) value;
        }
        DiskCache.DEntry entry = load(context, key);
        if (entry != null && entry.data != null) {
            value = ByteUtils.getFloat(entry.data);
        }

        return value == null ? 0 : (float) value;
    }

    /**
     * 缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void putAndSave(Context context, String key, Serializable value) {
        if (key != null) {
            put(key, value);
            save(context, key, ByteUtils.getBytes(value), value.getClass().getName());
        }
    }

    /**
     * 有效获取缓存项
     *
     * @param key
     * @return
     */
    protected Object getSerializable(Context context, String key) {
        Object value = get(key);
        if (value != null) {
            return value;
        }
        DiskCache.DEntry entry = load(context, key);
        if (entry != null && entry.data != null) {
            value = ByteUtils.getSerializable(entry.data);
        }

        return value;
    }

    /**
     * 缓存
     *
     * @param context
     * @param key
     * @param value
     */
    public void putAndSave(Context context, String key, String value) {
        if (key != null && value != null) {
            put(key, value);
            save(context, key, value.getBytes(), TYPE_STRING);
        }
    }

    /**
     * 有效获取缓存项
     *
     * @param key
     * @return
     */
    protected String getString(Context context, String key) {
        Object value = get(key);
        if (value != null) {
            return (String) value;
        }
        DiskCache.DEntry entry = load(context, key);
        if (entry != null && entry.data != null) {
            value = ByteUtils.getString(entry.data);
        }

        return (String) value;
    }

    public <T> Object getInner(Context context, String key) {
        Object value = get(key);
        if (value != null) {
            return value;
        }

        DiskCache.DEntry entry = load(context, key);
        String type = entry.etag;
        if (TextUtils.isEmpty(type)) {
            Log.e("", "key=" + key + "  error:type is null");
            return null;
        }

        if (TYPE_CHAR.equals(type)) {
            return getChar(context, key);
        }

        if (TYPE_DOUBLE.equals(type)) {
            return getDouble(context, key);
        }

        if (TYPE_FLOAT.equals(type)) {
            return getFloat(context, key);
        }

        if (TYPE_INT.equals(type)) {
            return getInteger(context, key);
        }

        if (TYPE_LONG.equals(type)) {
            return getLong(context, key);
        }

        if (TYPE_STRING.equals(type)) {
            return getString(context, key);
        }

        if (TYPE_SHORT.equals(type)) {
            return getShort(context, key);
        }

        try {
            Class ss = Class.forName(type);
            if (Serializable.class.isAssignableFrom(ss)) {
                return getSerializable(context, key);
            }

            if (Parcelable.class.isAssignableFrom(ss)) {
                //create creator;
                Parcelable.Creator<T> creator;
                Parcelable parcelable = (Parcelable) ss.newInstance();
                Field field = ss.getDeclaredField("CREATOR");
                if (field == null) {
                    return null;

                } else {
                    field.setAccessible(true);
                    creator = (Parcelable.Creator<T>) field.get(ss.newInstance());
                }

                return getParcelable(context, key, creator);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
}