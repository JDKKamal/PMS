package com.jdkgroup.pms.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceUtils {
    private final static String SP_NAME = "otc";
    private static PreferenceUtils preferenceUtils;
    private SharedPreferences sharedPreferences;
    private Context mContext;

    private PreferenceUtils(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences(SP_NAME, MODE_PRIVATE);
    }

    public static PreferenceUtils getInstance(Context mContext) {
        return preferenceUtils = (preferenceUtils == null ? new PreferenceUtils(mContext) : preferenceUtils);
    }

    private static void removeInstance() {
        preferenceUtils = null;
    }

    public void clearAllPrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        removeInstance();
    }
}
