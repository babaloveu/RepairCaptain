package com.example.lenovo.repaircaptain.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lenovo on 2018/3/26.
 */

public class MyListView extends ListView{
    public MyListView(Context context) {
        super(context);

    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(){
        this.setSelector(new ColorDrawable());//设置默认状态选择器为全透明
        this.setDivider(null);//去分隔线
        this.setCacheColorHint(Color.TRANSPARENT);

    }

}
