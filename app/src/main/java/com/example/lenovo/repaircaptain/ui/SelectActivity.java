package com.example.lenovo.repaircaptain.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.adapter.SelectAdapter;
import com.example.lenovo.repaircaptain.entity.SelectData;
import com.example.lenovo.repaircaptain.utils.ServerParams;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo on 2018/3/14.
 */

public class SelectActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_order_num;
    private Button btn_select;
    private ListView mListView;
    private List<SelectData> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_select);
        initView();

    }

    private  void initView(){
        et_order_num=(EditText) findViewById(R.id.et_order_num);
        btn_select=(Button)findViewById(R.id.btn_select);
        mListView=(ListView)findViewById(R.id.lv_order_select);
        btn_select.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_select:
                /*
                1获取输入框内容
                2判断是否为空
                3拿到数据去请求数据
                4解析Json
                5ListView适配器
                6实体类item
                7设置数据显示效果
                 */
                String number =et_order_num.getText().toString().trim();
                String url = ServerParams.host + "/order_status";
                HttpParams params = new HttpParams();
                params.put("id_order",number);
                if(!TextUtils.isEmpty(number)){
                    HttpCallback  callback = new HttpCallback(){
                        public void onSuccess(String t) {
                            parsingJson(t);
                        }

                    };
                    RxVolley.get(url, params, callback);

                }else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //解析数据
    private void parsingJson(String t){
        try {
            JSONArray jsonArray = new JSONArray(t);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject json =(JSONObject)jsonArray.get(i);
                SelectData data = new SelectData();
                data.setId(json.getInt("id"));
                data.setId_order(json.getInt("id_order"));
                data.setState(json.getString("order_state"));
                data.setDatatime(json.getString("time"));
                mList.add(data);
            }
            //倒叙处理
            Collections.reverse(mList);
            SelectAdapter adapter=new SelectAdapter(this,mList);
            mListView.setAdapter(adapter);

        }catch (JSONException e){
            e.printStackTrace();
        }

    }





}
