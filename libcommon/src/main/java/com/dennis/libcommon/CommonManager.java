package com.dennis.libcommon;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class CommonManager {
    private static CommonManager instance;

    private CommonManager(){}

    public static CommonManager getInstance(){
        if(instance == null){
            instance = new CommonManager();
        }
        return instance;
    }

    /**
     * 管理统一的create事项
     * module独立运行时可用
     * @param app
     */
    public void applicationCreate(Application app){
        ARouter.init(app);
    }


}
