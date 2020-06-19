package com.dennis.module_second;

import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lrx.module_base.base.BaseMVPActivity;

import butterknife.BindView;

@Route(path="/second/second")
public class SecondActivity extends BaseMVPActivity<ISecondView,SecondPresenter> implements ISecondView, View.OnClickListener {
    @BindView(R2.id.btn1)
    Button btn1;
    @Override
    public SecondPresenter createPresenter() {
        return new SecondPresenter();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.second_activity_second;
    }

    @Override
    protected void init() {
        super.init();
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
