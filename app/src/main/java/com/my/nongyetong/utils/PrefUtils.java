package com.my.nongyetong.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 保存SharedPreferences的一些数据 和 配置信息
 */

public class PrefUtils {
    public static final String SHRAR_PREFS_NAME= "nongyetong";
    public static void putBoolean(Context ctx,String key,boolean values){
        SharedPreferences sf = ctx.getSharedPreferences(SHRAR_PREFS_NAME,Context.MODE_PRIVATE);
        sf.edit().putBoolean(key,values).commit();
    }
    public static boolean getBoolean(Context ctx,String key,boolean defaultVaules){
        SharedPreferences sf = ctx.getSharedPreferences(SHRAR_PREFS_NAME,Context.MODE_PRIVATE);
        return sf.getBoolean(key,defaultVaules);
    }
    public static void putString(Context ctx, String key, String values){
        SharedPreferences sf = ctx.getSharedPreferences(SHRAR_PREFS_NAME,Context.MODE_PRIVATE);
        sf.edit().putString(key,values).commit() ;
    }
    public static String getString(Context ctx,String key,String defaultVaules){
        SharedPreferences sf = ctx.getSharedPreferences(SHRAR_PREFS_NAME,Context.MODE_PRIVATE);
        return sf.getString(key,defaultVaules);
    }
}
