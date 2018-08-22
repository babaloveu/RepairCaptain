package com.example.lenovo.repaircaptain.application;

import android.app.Application;

import com.example.lenovo.repaircaptain.utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * Created by lenovo on 2018/3/14.
 */

public class BaseApplication extends Application{
    public void onCreate(){
        super.onCreate();

        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);


    }

}
