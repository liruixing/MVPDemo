package com.lrx.module_base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * create by Dennis
 * on 2020-03-20
 * description：
 **/
public abstract class BaseMVPFragment<V extends IBaseMVPView, P extends BasePresenter<V>> extends BaseFragment {

    protected P mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initPersenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 创建对应的Presenter  某些特殊情况，presenter会作为一个工具类使用，一个activity对应多个presenter
     *
     * @return
     */
    public abstract P createPresenter();

    /**
     * 实例化presenter
     */
    public void initPersenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachMvpView((V) this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachMvpView();
        }
    }

}
