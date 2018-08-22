package com.example.lenovo.repaircaptain;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.example.lenovo.repaircaptain.fragment.HomeFragment;
import com.example.lenovo.repaircaptain.fragment.UserFragment;
import com.example.lenovo.repaircaptain.fragment.OrderFragment;
import com.example.lenovo.repaircaptain.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    //用来放title
    private List<String> mTitle;
    //用来放fragment
    private List<Fragment> mFragment;
    //悬浮气泡
    private FloatingActionButton fab_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.color_blue));
        //去掉阴影
        //getSupportActionBar().setElevation(0);
        initData();
        initView();
    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("首页");
        mTitle.add("订单");
        mTitle.add("我的");

        mFragment = new ArrayList<>();
        mFragment.add(new HomeFragment());
        mFragment.add(new OrderFragment());
        mFragment.add(new UserFragment());
    }

    private void initView() {
        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        //默认影藏
        fab_setting.setVisibility(View.GONE);
        mTablayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);


        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //mViewpagr设置滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "position" + position);
                if (position == 0) {
                    fab_setting.setVisibility(View.GONE);
                } else {
                    fab_setting.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //返回选中的Item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTablayout.setupWithViewPager(mViewPager);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                ((OrderFragment)mFragment.get(1)).updateDate();
//                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}





