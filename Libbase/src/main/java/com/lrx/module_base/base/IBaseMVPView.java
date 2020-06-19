package com.lrx.module_base.base;

import android.app.Activity;
import android.content.Context;

/**
 * create by Dennis
 * on 2020-01-06
 * description：
 **/
public interface IBaseMVPView {

    void writeString(String key, String value);

    String readString(String key);

    Activity getActivity();

}
