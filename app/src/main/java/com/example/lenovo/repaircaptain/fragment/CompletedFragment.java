package com.example.lenovo.repaircaptain.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.ui.DetailActivity;
import com.example.lenovo.repaircaptain.utils.ServerParams;
import com.example.lenovo.repaircaptain.utils.ShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lenovo on 2018/3/24.
 */

public class CompletedFragment extends android.support.v4.app.Fragment {
    ArrayList<Map<String, Object>> mData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed,null);
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData() {
        // 首先给出api地址
        String url = ServerParams.host + "/list_order";
        // 添加http查询参数也就是地址后面?后的参数
        HttpParams params = new HttpParams();
        // 再新建一个回调类，实现onSuccess方法，也就是调用成功后会调用这个onSuccess
        params.put("id_user", ShareUtils.getString(getActivity().getApplicationContext(), "id_user", ""));
        params.put("filter_status", "4");
        HttpCallback callback = new HttpCallback() {
            // 实现onSuccess
            @Override
            public void onSuccess(String t) {
                // t是返回的json数据，其实Json就是字符串，再声明一个ArrayList的mData，通过Gson把json转换为ArrayList<Map<String, Object>>类型
                mData = new Gson().fromJson(t, new TypeToken<ArrayList<Map<String, Object>>>() {
                        }.getType());
                // 后面设置Adapter
                ListView list = getActivity().findViewById(R.id.lv_order_completed);
                // 注意这里，new String[]{"name", "type"}表示的你要显示的数据，name和type就是显示的数据
                SimpleAdapter adapter = new SimpleAdapter(getActivity(), mData, R.layout.list_item_order,
                        new String[]{"id", "name_machine", "level", "address", "time_creation"},
                        new int[]{R.id.tv_order_id, R.id.tv_name_machine, R.id.tv_level, R.id.tv_address, R.id.tv_item_time_creation});
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (parent.getId()) {
                            case R.id.lv_order_completed:
                                expressitemClick(position);
                                break;
                        }
                    }
                });
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
            }
        };
        // RxVolley简单实现，把地址url，查询参数params，回调对象callback，三个对象传入，网路请求就开始了，成功后会调用回调对象的onSuccess
        RxVolley.get(url, params, callback);
    }

    public void expressitemClick(int position) {
        int idOrder = (int)(double)mData.get(position).get("id");
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id_order", idOrder);
        startActivity(intent);
    }

}
