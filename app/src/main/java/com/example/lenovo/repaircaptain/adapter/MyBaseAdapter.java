package com.example.lenovo.repaircaptain.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.lenovo.repaircaptain.holder.BaseHolder;

import java.util.ArrayList;

/**
 * Created by lenovo on 2018/3/26.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter
{

    private ArrayList<T> data;


public MyBaseAdapter(ArrayList<T> data){

    this.data = data;



}
    @Override
    public int getCount() {

    return data.size();
    }

    @Override
    public T getItem(int position) {

    return data.get(position);
    }

    @Override
    public long getItemId(int position) {

    return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if(convertView == null){
        holder =getHolder();//子类返回具体对象
        }else{
            holder =(BaseHolder)convertView.getTag();
        }
    holder.setData(getItem(position));
    return holder.getRootView();
    }

    //返回当前页面holder对象，必须子类实现
    public abstract BaseHolder<T> getHolder();


}
