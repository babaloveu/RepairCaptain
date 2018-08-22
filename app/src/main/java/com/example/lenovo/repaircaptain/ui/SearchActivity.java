package com.example.lenovo.repaircaptain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.lenovo.repaircaptain.MainActivity;
import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.entity.ErrMsg;
import com.example.lenovo.repaircaptain.entity.OrderListModel;
import com.example.lenovo.repaircaptain.utils.ServerParams;
import com.example.lenovo.repaircaptain.utils.ShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_search_keywords;
    private Button btn_search;
    private ImageButton ib_back;
    public ListView lv_searching_result;
    private ArrayList<Map<String, Object>> mData;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search);
//        对应组件
        et_search_keywords = (EditText)findViewById(R.id.et_search_keywords);
        btn_search = (Button)findViewById(R.id.btn_search);
        lv_searching_result = findViewById(R.id.lv_searching_result);
        ib_back =(ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    public void onClick(View v) {


        switch (v.getId()){
            case R.id.btn_search:
                search();
                break;
            case R.id.ib_back:
                finish();
                break;
        }


    }



    private void search() {
        HttpParams params = new HttpParams();
        // 这里有两步，先gson转为json，再putjson到params里面
        params.put("key", et_search_keywords.getText().toString());
        params.put("id_user", ShareUtils.getString(getApplicationContext(), "id_user", ""));
        // 使用了高级用法，其实是一样的，设置url，请求方法，参数，请求内容类型等
        new RxVolley.Builder()
                // 这里我加了一个静态类，host就是api的地址，直接在后面追加进行操作的地址就OK
                .url(ServerParams.host + "/search_order")
                .httpMethod(RxVolley.Method.GET)
                .contentType(RxVolley.ContentType.FORM)
                .params(params)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        mData = new Gson().fromJson(t, new TypeToken<ArrayList<Map<String, Object>>>() {
                        }.getType());
                        // 注意这里，new String[]{"name", "type"}表示的你要显示的数据，name和type就是显示的数据
                        if (mData.size() == 0) {
                            Toast.makeText(getApplicationContext(), "没有查到任何数据", Toast.LENGTH_SHORT).show();
                        }
                        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), mData, R.layout.list_item_order,
                                new String[]{"id", "name_machine", "level", "address", "time_creation"},
                                new int[]{R.id.tv_order_id, R.id.tv_name_machine, R.id.tv_level, R.id.tv_address, R.id.tv_item_time_creation});
                        lv_searching_result.setAdapter(adapter);
                        lv_searching_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                switch (parent.getId()) {
                                    case R.id.lv_searching_result:
                                        expressitemClick(position);
                                        break;
                                }
                            }
                        });
                    }

                }) //响应回调
                .encoding("UTF-8") //编码格式，默认为utf-8
                .doTask();  //执行请求操作
    }
    public void expressitemClick(int position) {
        int idOrder = (int)(double)mData.get(position).get("id");
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id_order", idOrder);
        startActivity(intent);
    }
}
