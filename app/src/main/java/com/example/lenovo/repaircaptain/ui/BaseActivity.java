package com.example.lenovo.repaircaptain.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by lenovo on 2018/3/14.
 * activity基类
 * 1.统一的属性
 * 2统一的接口
 * 3统一的方法
 *
 */

public class BaseActivity extends AppCompatActivity{
    @Override
   protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    //菜单栏事件

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId()){
           case android.R.id.home:
               finish();
               break;
       }
        return super.onOptionsItemSelected(item);
    }
}
