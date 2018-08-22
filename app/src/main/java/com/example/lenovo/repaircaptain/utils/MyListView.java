package com.example.lenovo.repaircaptain.utils;

import android.widget.ListView;

/**
 * Created by lenovo on 2018/4/30.
 */

public class MyListView extends ListView {
    public MyListView(android.content.Context context,android.util.AttributeSet attrs){
        super(context, attrs);
    }
    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
