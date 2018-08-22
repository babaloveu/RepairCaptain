package com.example.lenovo.repaircaptain.adapter;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.entity.OrderListModel;


import java.util.List;

/**
 * Created by lenovo on 2018/4/9.
 */

public class OrderAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<OrderListModel> mList;
    private OrderListModel data;

    public OrderAdapter(Context mContext, List<OrderListModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_order, null);
            viewHolder.tv_order_id = (TextView) convertView.findViewById(R.id.tv_order_id);
//            viewHolder.tv_status_id = (TextView) convertView.findViewById(R.id.tv_status_id);
//            viewHolder.tv_order_name = (TextView) convertView.findViewById(R.id.tv_order_name);
//            viewHolder.tv_order_type = (TextView) convertView.findViewById(R.id.tv_order_type);
//            viewHolder.tv_time_craetion = (TextView) convertView.findViewById(R.id.tv_time_creation);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        viewHolder.tv_order_id.setText(String.valueOf(data.getId()));
//        viewHolder.tv_status_id.setText(String.valueOf(data.getId_status()));
//        viewHolder.tv_order_name.setText(String.valueOf(data.getName()));
//        viewHolder.tv_order_type.setText(String.valueOf(data.getType()));
//        viewHolder.tv_time_craetion.setText(String.valueOf(data.getTime_creation()));

//        if (data.getIsdispatch())
//            viewHolder.tv_status_id
       return convertView;


    }

    class ViewHolder{
        private TextView tv_order_id;
        private TextView tv_status_id;
        private TextView tv_order_name;
        private TextView tv_order_type;
        private TextView tv_time_craetion;
    }


}
