package com.example.lenovo.repaircaptain.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.repaircaptain.R;

/**
 * Created by lenovo on 2018/3/27.
 */

public class HomeHolder extends BaseHolder<String> {
    private LayoutInflater Inflater;
    private ViewGroup parent;
    private TextView mIntro;

    @Override
    public View initView(){
        View view = Inflater.inflate(R.layout.list_item_home,parent,false);
        mIntro = (TextView)view.findViewById(R.id.tv_intro);

        return view;
    }



    @Override
    public void refreshView(String data) {
        mIntro.setText(data);

    }
}
