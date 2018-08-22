package com.example.lenovo.repaircaptain.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.entity.HomeList;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lenovo on 2018/5/1.
 */

public class HomeListAdapter extends ArrayAdapter {
    private final int resourceId;
    public HomeListAdapter(@NonNull Context context, int resource, List<HomeList> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeList homeList = (HomeList) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView Image = (ImageView) view.findViewById(R.id.iv_icon);
        TextView Name = (TextView) view.findViewById(R.id.tv_name);
        TextView Intro = (TextView)view.findViewById(R.id.tv_intro);
        RatingBar Rb_star =(RatingBar)view.findViewById(R.id.rb_star);
        Image.setImageResource(homeList.getImageId());
        Name.setText(homeList.getName());
        Intro.setText(homeList.getIntro());
        Rb_star.setRating(homeList.getRating());
        return view;
    }

}
