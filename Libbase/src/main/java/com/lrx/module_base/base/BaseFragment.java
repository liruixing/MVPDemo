package com.lrx.module_base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lrx.module_base.utils.SPhelper;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * create by Dennis
 * on 2020-03-20
 * description：
 **/
public abstract class BaseFragment extends Fragment implements IBaseMVPView {

    //传递过来的参数Bundle，供子类使用
    protected Bundle args;
    private Unbinder mUnbinder;
    private View rootView;// 缓存Fragment view

    /**
     * 创建fragment的静态方法，方便传递参数
     *
     * @param args 传递的参数
     * @return
     */
    public static <T extends Fragment> T getInstance(Class clazz, Bundle args) {
        T mFragment = null;
        try {
            mFragment = (T) clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mFragment.setArguments(args);
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getContentViewResId(), container, false);
            mUnbinder = ButterKnife.bind(this, rootView);
            initView(rootView);
        } else {
            mUnbinder = ButterKnife.bind(this, rootView);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    protected abstract int getContentViewResId();

    protected abstract void initView(View view);

    /**
     * 初始创建Fragment对象时调用
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void writeString(String key, String value) {
        SPhelper.getInstance().writeString(getActivity(), key, value);
    }

    @Override
    public String readString(String key) {
        return SPhelper.getInstance().readString(getActivity(), key);
    }

}
