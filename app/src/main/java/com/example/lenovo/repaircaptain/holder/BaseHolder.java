package com.example.lenovo.repaircaptain.holder;

import android.view.View;

/**
 * Created by lenovo on 2018/3/27.
 */

public abstract class BaseHolder<T> {
    private View mRootView;//一个item的根布局
    private T data;
    public BaseHolder(){
        mRootView =initView();
        //3打一个标记tag
        mRootView.setTag(this);
    }

    //1加载布局
    //2初始化控件
    public abstract View initView();

//返回item布局文件
    public View getRootView()
    {
        return mRootView;
    }
    public void setData(T data){
        this.data = data;
        refreshView(data);
    }

    public T getData(){
        return data;
    }
//4根据数据刷新界面
    public abstract void refreshView(T data);
}
