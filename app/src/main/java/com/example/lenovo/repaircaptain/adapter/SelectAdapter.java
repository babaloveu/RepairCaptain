package com.example.lenovo.repaircaptain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.entity.SelectData;

import java.util.List;

/**
 * 订单查询
 * Created by lenovo on 2018/3/29.
 */

public class SelectAdapter extends BaseAdapter {
   private Context mContext;
   private List<SelectData> mList;
   private LayoutInflater inflater;
   private SelectData data;


    public SelectAdapter(Context mContext,List<SelectData> mList){
        this.mContext=mContext;
        this.mList=mList;
        //获取系统服务
        inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        if(convertView==null){
            viewHolder =new ViewHolder();
            convertView =inflater.inflate(R.layout.select_item,null);
            viewHolder.tv_state =(TextView) convertView.findViewById(R.id.tv_state);
            viewHolder.tv_datetime =(TextView) convertView.findViewById(R.id.tv_datetime);
            //设置缓存
            convertView.setTag(viewHolder);

        }else{
             viewHolder =(ViewHolder) convertView.getTag();
        }
        //设置数据
        data =mList.get(position);
        viewHolder.tv_state.setText(data.getState());
        viewHolder.tv_datetime.setText(data.getDatatime());
        return convertView;
    }

    class ViewHolder{

        private TextView tv_state;
        private TextView tv_datetime;


    }
}
