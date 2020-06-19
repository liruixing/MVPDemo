package com.lrx.module_base.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * create by Dennis
 * on 2020-01-06
 * description：
 **/
public class BasePresenter<V extends IBaseMVPView> {

    protected V mView;
    private WeakReference<V> weakReferenceView;
    protected CompositeDisposable mCompositeDisposable;


    //防止空指针 获取到接口层
    protected V getView() {
        if (mView == null) {
            throw new IllegalStateException("view not attached");
        } else {
            return mView;
        }
    }

    /**
     * 绑定view
     *
     * @param view
     */
    public void attachMvpView(V view) {
        mCompositeDisposable = new CompositeDisposable();
        if (view != null) {
            weakReferenceView = new WeakReference<>(view);
            this.mView = weakReferenceView.get();
        }

    }

    /**
     * 解除绑定view
     */
    public void detachMvpView() {
        weakReferenceView.clear();
        mCompositeDisposable.dispose();
        weakReferenceView = null;
        mView = null;
    }

}
