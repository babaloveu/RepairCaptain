package com.example.lenovo.repaircaptain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.repaircaptain.MainActivity;
import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by lenovo on 2018/3/15.
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private List<View> mList = new ArrayList<>();
    private View view1,view2,view3;
    //跳过按钮
    private ImageView iv_back;
    private TextView tv_p1,tv_p2,tv_p3;


    //小圆点
    private ImageView point1,point2,point3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();

    }

    private void initView(){
        mViewPager=(ViewPager)findViewById(R.id.mViewPager);
        point1=(ImageView) findViewById(R.id.point1);
        point2=(ImageView) findViewById(R.id.point2);
        point3=(ImageView) findViewById(R.id.point3);
        //设置默认选中第一个小圆点
        setPointImg(true,false,false);
        view1 = View.inflate(this,R.layout.pager_item1,null);
        view2 = View.inflate(this,R.layout.pager_item2,null);
        view3 = View.inflate(this,R.layout.pager_item3,null);

        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        tv_p1 = (TextView) view1.findViewById(R.id.tv_p1);
        tv_p2 = (TextView) view2.findViewById(R.id.tv_p2);
        tv_p3 = (TextView) view3.findViewById(R.id.tv_p3);

        UtilTools.setFont(this,tv_p1);
        UtilTools.setFont(this,tv_p2);
        UtilTools.setFont(this,tv_p3);

        iv_back =(ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setPointImg(true,false,false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false,true,false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false,false,true);
                        iv_back.setVisibility(View.GONE);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
            case R.id.iv_back:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;


        }
    }

    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {

            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(mList.get(position));

           // super.destroyItem(container, position, object);
        }
    }

    //设置小圆点
    public void setPointImg(boolean isSelected1,boolean isSelected2,boolean isSelected3){
        if(isSelected1){
        point1.setBackgroundResource(R.drawable.point_on);
        }else{
            point1.setBackgroundResource(R.drawable.point_off);
        }
        if(isSelected2){
            point2.setBackgroundResource(R.drawable.point_on);
        }else{
            point2.setBackgroundResource(R.drawable.point_off);
        }
        if(isSelected3){
            point3.setBackgroundResource(R.drawable.point_on);
        }else{
            point3.setBackgroundResource(R.drawable.point_off);
        }

    }




}
