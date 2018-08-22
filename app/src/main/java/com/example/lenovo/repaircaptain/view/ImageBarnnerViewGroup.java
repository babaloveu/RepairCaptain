package com.example.lenovo.repaircaptain.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**实现轮播广告
 * Created by lenovo on 2018/3/22.
 */

public class ImageBarnnerViewGroup extends ViewGroup {

    private int children;
    private int childwidth;
    private int childheight;
    private int x;
    private int index=0;
    private Scroller scroller;

    public ImageBarnnerViewGroup(Context context) {

        super(context);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {

        super(context, attrs);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj(){
        scroller = new Scroller(getContext());

    }

    public  void computeScroll(){
        super.computeScroll();
        if(scroller.computeScrollOffset()){

        }
    }

    //自定义viewgroup 必须实现的方法的 测量--布局--绘制

    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        //1求出子视图的个数
        children = getChildCount();
        if(0==children){
            setMeasuredDimension(0,0);

        }else{
            //2测量子视图的宽度和高度
            measureChildren(widthMeasureSpec,heightMeasureSpec);
            View view =getChildAt(0);
             childheight = view.getMeasuredHeight();
             childwidth = view.getMeasuredWidth();
             int width =childwidth*children;
            setMeasuredDimension(width,childheight);
        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = (int)event.getX();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int moveX = (int) event.getX();
                        int distance = moveX - x;
                        scrollBy(-distance,0);
                        x=moveX;
                            break;
                    case MotionEvent.ACTION_UP:
                        int scrollX = getScrollX();
                        index =(scrollX+childwidth/2)/childwidth;
                        if(index<0){//说明此时已经滑到了最左边第一张图
                            index=0;
                        }else if(index>children -1){//说明滑到了最后一张图
                            index=children -1;
                        }
                        scrollTo(index*childwidth,0);
                        break;
                        default:
                            break;
                }

        return true;//返回true 的目的是告诉我们该View group容器的父view 我们意见处理好了
    }

    //绘制
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int leftMargin = 0;
            for(int i =0;i<children;i++){
                View view = getChildAt(i);
                view.layout(leftMargin,0,leftMargin+childwidth,childheight);
                leftMargin+=childwidth;
            }
        }

    }



}
