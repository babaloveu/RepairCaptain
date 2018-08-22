package com.example.lenovo.repaircaptain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.repaircaptain.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/3/14.
 */

public class OrderFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //用来放title
    private List<String> mTitle;
    //用来放fragment
    private List<Fragment> mFragment;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);
        initData();
        initView(view);
        return view;
    }


    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("全部");
        mTitle.add("未维修");
        mTitle.add("正维修");
        mTitle.add("已完成");

        mFragment = new ArrayList<>();
        mFragment.add(new AllFragment());
        mFragment.add(new UnRepaireFragment());
        mFragment.add(new RepairingFragment());
        mFragment.add(new CompletedFragment());
    }


    //初始化view
    private void initView(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        //预加载
        viewPager.setOffscreenPageLimit(mFragment.size());
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }

        });
        tabLayout.setupWithViewPager(viewPager);
    }
    public void updateDate() {
        ((AllFragment) mFragment.get(0)).getData();
        ((UnRepaireFragment) mFragment.get(1)).getData();
        ((RepairingFragment) mFragment.get(2)).getData();
        ((CompletedFragment) mFragment.get(3)).getData();
    }
}
