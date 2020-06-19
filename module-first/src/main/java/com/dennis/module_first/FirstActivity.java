package com.dennis.module_first;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dennis.libcommon.CommonManager;
import com.lrx.module_base.base.BaseMVPActivity;

import butterknife.BindView;


@Route(path="/first/first")
public class FirstActivity extends BaseMVPActivity<IFirstView,FirstPresenter> implements IFirstView , View.OnClickListener {
    @BindView(R2.id.btn1)
    Button btn1;
    @BindView(R2.id.btn2)
    Button btn2;

    @Override
    public FirstPresenter createPresenter() {
        return new FirstPresenter();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.first_activity_main;
    }

    @Override
    protected void init() {
        super.init();
//        CommonManager.getInstance().applicationCreate(getApplication());
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn1) {
            ARouter.getInstance().build("/second/second").navigation();
        } else if (id == R.id.btn2) {
            Toast.makeText(this,"ceshi",Toast.LENGTH_LONG).show();
        }
    }
}
