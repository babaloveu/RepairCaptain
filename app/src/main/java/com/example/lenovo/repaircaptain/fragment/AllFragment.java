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
import com.example.lenovo.repaircaptain.entity.OrderListModel;
import com.example.lenovo.repaircaptain.ui.DetailActivity;
import com.example.lenovo.repaircaptain.utils.ServerParams;
import com.example.lenovo.repaircaptain.utils.ShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/3/24.
 */

public class AllFragment extends android.support.v4.app.Fragment {
    private ListView mListView;
    ArrayList<Map<String, Object>> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_all, null);
        onStart();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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
        HttpCallback callback = new HttpCallback() {
            // 实现onSuccess
            @Override
            public void onSuccess(String t) {
                // t是返回的json数据，其实Json就是字符串，再声明一个ArrayList的mData，通过Gson把json转换为ArrayList<Map<String, Object>>类型
                mData = new Gson().fromJson(t, new TypeToken<ArrayList<Map<String, Object>>>() {
                        }.getType());
                // 后面设置Adapter
                ListView list = getActivity().findViewById(R.id.lv_order_all);
                // 注意这里，new String[]{"name", "type"}表示的你要显示的数据，name和type就是显示的数据
                SimpleAdapter adapter = new SimpleAdapter(getActivity(), mData, R.layout.list_item_order,
                        new String[]{"id", "name_machine", "level", "address", "time_creation"},
                        new int[]{R.id.tv_order_id, R.id.tv_name_machine, R.id.tv_level, R.id.tv_address, R.id.tv_item_time_creation});
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (parent.getId()) {
                            case R.id.lv_order_all:
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
        System.out.println(idOrder);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id_order", idOrder);
        startActivity(intent);
    }


//    //解析数据
//    private void parsingJson(String t){
//        try {
//            JSONArray jsonArray = new JSONArray(t);
//            for(int i=0;i<jsonArray.length();i++){
//                JSONObject jsonObject =(JSONObject)jsonArray.get(i);
//                OrderListModel data = new OrderListModel();
//                data.setId(jsonObject.getInt("id"));
//                data.setName_machine(jsonObject.getString("name_machine"));
//                data.setLevel(jsonObject.getString("level"));
//                data.setId_address(jsonObject.getInt("id_woker"));
//                AddressModel addressModel = new AddressModel();
//                JSONObject jsonObject1 = jsonObject.getJSONObject("address");
//                addressModel.setId(jsonObject1.getInt("id"));
//                addressModel.setId_user(jsonObject1.getInt("id_user"));
//                addressModel.setProvince(jsonObject1.getString("province"));
//                addressModel.setCity(jsonObject1.getString("city"));
//                addressModel.setDistrict(jsonObject1.getString("district"));
//                addressModel.setDetail(jsonObject1.getString("detail"));
//                addressModel.setPlace(jsonObject1.getString("place"));
//                mList.add(data);
//            }
//            //倒叙处理
//            Collections.reverse(mList);
//            SelectAdapter adapter=new SelectAdapter(this,mList);
//            mListView.setAdapter(adapter);
//
//        }catch (JSONException e){
//            e.printStackTrace();
//        }


}
