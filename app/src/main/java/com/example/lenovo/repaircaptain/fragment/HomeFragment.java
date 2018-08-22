package com.example.lenovo.repaircaptain.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.adapter.HomeListAdapter;
import com.example.lenovo.repaircaptain.entity.HomeList;
import com.example.lenovo.repaircaptain.ui.CreateActivity;
import com.example.lenovo.repaircaptain.ui.LocationActivity;
import com.example.lenovo.repaircaptain.ui.SearchActivity;
import com.example.lenovo.repaircaptain.ui.SuggestionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/3/14.
 */

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager;
    private ArrayList<ImageView> imageViewList;
    private ImageView imageView;
    private LinearLayout ll_point_container;
    private LinearLayout.LayoutParams layoutParams;
    private int previous;
    private int[] imageResIds;
    private ImageButton btn_create;
    private ImageButton btn_search;
    private ImageButton btn_location;
    private ImageButton btn_sugg;
    private ImageButton btn_contact;
    private ListView listView;
    private ArrayList<String> data;
    private View mHeaderView;
    private List<HomeList> homeList = new ArrayList<HomeList>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        initView(view);
        initData();
        initAdapter();
        //开始自动轮播
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (getActivity() != null)
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            }
                        });
                }
            }

            ;
        }.start();

        return view;
    }

    public void initView(View view) {

        viewPager = (ViewPager) view.findViewById(R.id.vp);
        viewPager.addOnPageChangeListener(this);
        ll_point_container = (LinearLayout) view.findViewById(R.id.ll_point_container);
        btn_create = (ImageButton) view.findViewById(R.id.btn_create);
        btn_create.setOnClickListener(this);
        btn_search = (ImageButton) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        btn_location = (ImageButton) view.findViewById(R.id.btn_location);
        btn_location.setOnClickListener(this);
        btn_sugg = (ImageButton) view.findViewById(R.id.btn_sugg);
        btn_sugg.setOnClickListener(this);
        btn_contact = (ImageButton) view.findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.lv_home);
        // mHeaderView = view.inflate(getActivity(),R.layout.list_item_header,null);
        //listView.addHeaderView(mHeaderView);


    }


    public void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        previous = 0;
        viewPager.setAdapter(new MyAdapter());
        viewPager.setCurrentItem(5000000);
        listView.setAdapter(new HomeListAdapter(getActivity(), R.layout.list_item_home, homeList));


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % imageViewList.size();
        ll_point_container.getChildAt(previous).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);
        previous = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create:
                startActivity(new Intent(getActivity(), CreateActivity.class));
                break;
            case R.id.btn_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.btn_location:
                startActivity(new Intent(getActivity(), LocationActivity.class));
                break;
            case R.id.btn_sugg:
                startActivity(new Intent(getActivity(), SuggestionActivity.class));
                break;
            case R.id.btn_contact:
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());
                builder.setMessage("是否拨打客服电话？").setPositiveButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phone_number = "13720245671";
                        Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                                + phone_number));
                        startActivity(intent2);
                    }
                }).show();
                break;


        }
    }

    class HomeAdapter extends BaseAdapter {


        @Override
        public int getCount() {

            return 5;
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getContext(), R.layout.list_item_home, null);
            } else {
                view = convertView;
            }

            return view;
        }
    }


    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //当滑倒新的条目，又返回来，view是否可以复用
            //返回判断规则
            return view == object;
        }

        //创建要显示的条目内容
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newPosition = position % imageViewList.size();
            ImageView imageView = imageViewList.get(newPosition);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //object要销毁的对象
            container.removeView((View) object);
        }
    }


    public void initData() {
        imageResIds = new int[]{R.drawable.a, R.drawable.b,
                R.drawable.c, R.drawable.d, R.drawable.e};
        //初始化要展示的ImageView
        imageViewList = new ArrayList<ImageView>();
        View pointView;
        HomeList wang = new HomeList("王师傅", R.drawable.wang, "入职八年，维修经验丰富。擅长小家电维修，维修效率高，平均50分钟搞定一台机器。", 4);
        homeList.add(wang);
        HomeList xiao = new HomeList("肖师傅", R.drawable.xiao, "入职六年，维修经验丰富。连续三年获得优秀员工奖，顾客好评无数。", 5);
        homeList.add(xiao);
        HomeList ma = new HomeList("马师傅", R.drawable.ma, "入职七年，给上千家顾客解决疑难杂症。服务细心，曾经获得服务之星的称号。", 5);
        homeList.add(ma);
        HomeList liu = new HomeList("刘师傅", R.drawable.liu, "仅仅工作三年，就取得了高级维修工程认证，是我公司黑马员工", 4);
        homeList.add(liu);
        HomeList zhang = new HomeList("李师傅", R.drawable.zhang, "国家二级维修技师证书获得者，解决问题高效，方法独到，好评无数", 5);
        homeList.add(zhang);


        for (int i = 0; i < imageResIds.length; i++) {
            imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            //加小点 指示器
            pointView = new View(getActivity());
            pointView.setBackgroundResource(R.drawable.shape_bg_point_enable);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0)
                layoutParams.leftMargin = 10;
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);


        }
        // getDataFromServer();


    }


}



