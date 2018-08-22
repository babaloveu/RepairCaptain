package com.example.lenovo.repaircaptain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.utils.ShareUtils;
import com.example.lenovo.repaircaptain.utils.StaticClass;
import com.example.lenovo.repaircaptain.utils.UtilTools;

/**闪屏页
 * Created by lenovo on 2018/3/15.
 */

public class SplashActivity extends AppCompatActivity {
    /*
    1.延时2000ms
    2.判断程序是否第一次运行
    3.自定义字体
    4.Activity全屏主题
     */


    private TextView tv_splash;
    private LinearLayout rlRoot;
    private ImageView iv_icon;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if(isFirst()){
                       startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }
                    finish();
                    break;

            }
        }
    };


    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        rlRoot =(LinearLayout) findViewById(R.id.rl_root);



        //旋转动画
        RotateAnimation animRotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animRotate.setDuration(1000);
        animRotate.setFillAfter(true);

        //缩放动画
        ScaleAnimation animScale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        animScale.setDuration(1000);
        animScale.setFillAfter(true);

        //渐变动画
        AlphaAnimation animAlpha = new AlphaAnimation(0,1);
        animAlpha.setDuration(2000);
        animAlpha.setFillAfter(true);

        //动画集合
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(animRotate);
        set.addAnimation(animScale);
        set.addAnimation(animAlpha);

        iv_icon.startAnimation(set);





    }

    public void  initView(){
        //延时
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        tv_splash = (TextView)findViewById(R.id.tv_splash);
        iv_icon=(ImageView)findViewById(R.id.iv_icon);

        //设置字体
        UtilTools.setFont(this,tv_splash);
    }


//判断是否是第一次运行
    private boolean isFirst(){
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            //如果是第一次运行那么把标记改为false
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else{
            return false;}
    }

    //禁止返回键
    public void onBackPressed(){

    }



}
