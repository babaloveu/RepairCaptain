package com.example.lenovo.repaircaptain.ui;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.lenovo.repaircaptain.R;

/**
 * Created by lenovo on 2018/3/14.
 */

public class SettingActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.color_blue));
    }
}
