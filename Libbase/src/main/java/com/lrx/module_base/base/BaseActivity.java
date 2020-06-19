package com.lrx.module_base.base;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lrx.module_base.manager.AppManagerUtil;
import com.lrx.module_base.utils.SPhelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * create by Dennis
 * on 2020-01-06
 * description：
 **/
public abstract class BaseActivity extends FragmentActivity implements IBaseMVPView{

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        AppManagerUtil.getInstance().addActivity(this);
        mUnbinder = ButterKnife.bind(this);
        init();
        ARouter.getInstance().inject(this);
    }

    protected void init() {}

    protected abstract int getContentViewResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void writeString(String key, String value) {
        SPhelper.getInstance().writeString(this, key, value);
    }

    @Override
    public String readString(String key) {
        return SPhelper.getInstance().readString(this, key);
    }

    @Override
    public Resources.Theme getTheme() {
        return super.getTheme();
    }
}
