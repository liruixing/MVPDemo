package com.lrx.module_base.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * create by Dennis
 * on 2020-01-06
 * description：
 **/
public class SPhelper {
    private static SPhelper instance = null;
    private final String fileName = "smasSetting";

    private SPhelper() {
    }

    public static SPhelper getInstance() {
        if (instance == null) {
            instance = new SPhelper();
        }
        return instance;
    }

    public void writeString(Context ctx, String key, String data) {
        SharedPreferences sp = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public String readString(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String data = sp.getString(key, "");
        return data;
    }

}
