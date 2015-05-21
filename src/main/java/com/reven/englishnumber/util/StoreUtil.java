package com.reven.englishnumber.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by CS on 2015/5/12.
 * Easy to use SharedPreference
 */
public class StoreUtil {
    private SharedPreferences mPreferences;

    public StoreUtil(Context context) {
        this(context, "store");
    }

    public StoreUtil(Context context, String name) {
        mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public void putString(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        mPreferences.edit().putInt(key, value).apply();
    }
}
