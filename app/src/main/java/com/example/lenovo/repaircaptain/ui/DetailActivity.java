package com.example.lenovo.repaircaptain.ui;

import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.adapter.SelectAdapter;
import com.example.lenovo.repaircaptain.entity.ErrMsg;
import com.example.lenovo.repaircaptain.entity.OrderModel;
import com.example.lenovo.repaircaptain.entity.SelectData;
import com.example.lenovo.repaircaptain.entity.UserModel;
import com.example.lenovo.repaircaptain.utils.MyListView;
import com.example.lenovo.repaircaptain.utils.ServerParams;
import com.example.lenovo.repaircaptain.utils.ShareUtils;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2018/4/16.
 */

public class DetailActivity extends BaseActivity implements View.OnClickListener{
    private TextView id_order;
    private TextView time_creation;
    private EditText machine;
    private EditText description;
    private EditText address;
    private EditText time1;
    private EditText time2;
    private EditText level;
    private TextView status;
    private TextView name_worker;
    private TextView phone_worker;
    private Button btn_edit;
    private Button btn_save;
    private ImageButton btn_more;
    private MyListView mListView;
    private List<SelectData> mList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.color_blue));
        initView();
        getData();

    }

    private void initView() {
        id_order = (TextView) findViewById(R.id.id_order);
        time_creation = (TextView) findViewById(R.id.time_creation);
        machine = (EditText) findViewById(R.id.machine);
        description = (EditText) findViewById(R.id.description);
        address = (EditText) findViewById(R.id.address);
        time1 = (EditText) findViewById(R.id.time1);
        time2 = (EditText) findViewById(R.id.time2);
        level = (EditText) findViewById(R.id.level);
        status =(TextView) findViewById(R.id.status);
       // name_worker=(TextView)findViewById(R.id.name_worker);
        //phone_worker=(TextView)findViewById(R.id.phone_worker);
        btn_edit=(Button)findViewById(R.id.btn_edit);
        btn_save=(Button)findViewById(R.id.btn_save);
        btn_more =(ImageButton)findViewById(R.id.btn_more);
        btn_edit.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        mListView=(MyListView)findViewById(R.id.lv_order_select);


        //默认是不可编辑的

        machine.setEnabled(false);
        description.setEnabled(false);
        address.setEnabled(false);
        time1.setEnabled(false);
        time2.setEnabled(false);
        level.setEnabled(false);
    }

    public void getData(){
        String url = ServerParams.host + "/order";
        int idOrder = this.getIntent().getIntExtra("id_order", 0);
        HttpParams params = new HttpParams();
        params.put("id", idOrder);
        HttpCallback callback = new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                try{
                System.out.println(t);
                    Gson gson = new Gson();
                    OrderModel orderModel = gson.fromJson(t,OrderModel.class);
                    System.out.println(orderModel);
                    id_order.setText(String.valueOf(orderModel.getId()));
                    time_creation.setText(String.valueOf(orderModel.getTime_creation()));
                    machine.setText(String.valueOf(orderModel.getName_machine()));
                    description.setText(String.valueOf(orderModel.getDescription()));
                    address.setText(String.valueOf(orderModel.getAddress()));
                    time1.setText(String.valueOf(orderModel.getTime1()));
                    time2.setText(String.valueOf(orderModel.getTime2()));
                    level.setText(String.valueOf(orderModel.getLevel()));
                    if (orderModel.getStatus() != null)
                        status.setText(String.valueOf(orderModel.getStatus().getOrder_statue()));
//                    name_worker.setText(String.valueOf(orderModel.getWorker().getName()));
//                    phone_worker.setText(String.valueOf(orderModel.getWorker().getPhone1()));

                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }

        };

        RxVolley.get(url, params, callback);

    }

    private void setEnabled(boolean is){
        machine.setEnabled(is);
        description.setEnabled(is);
        address.setEnabled(is);
        time1.setEnabled(is);
        time2.setEnabled(is);
        level.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                setEnabled(true);
                break;
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_more:
                String url = ServerParams.host + "/order_status";
                HttpParams params = new HttpParams();
                params.put("id_order",ShareUtils.getString(getApplicationContext(), "id_user", ""));
                    HttpCallback  callback = new HttpCallback(){
                        public void onSuccess(String t) {
                            try {
                                System.out.println(t);
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
                                SelectAdapter adapter=new SelectAdapter(getApplicationContext(),mList);
                                mListView.setAdapter(adapter);

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }

                    };
                    RxVolley.get(url, params, callback);



                break;
        }
    }

    public void save(){
        // 新建一个map对象存储数据
        HashMap<String, Object> form = new HashMap<>();
        form.put("id", id_order.getText().toString());
        form.put("id_user",ShareUtils.getString(this,"id_user","1"));
        //form.put("id_address",OrderModel.);
        form.put("time_creation", time_creation.getText().toString());
        form.put("address", address.getText().toString());
        form.put("name_machine", machine.getText().toString());
        form.put("description", description.getText().toString());
        form.put("time1", time1.getText().toString());
        form.put("time2", time2.getText().toString());
        form.put("level", level.getText().toString());
        HttpParams params = new HttpParams();
        // 这里有两步，先gson转为json，再putjson到params里面
        params.putJsonParams(new Gson().toJson(form));
        // 使用了高级用法，其实是一样的，设置url，请求方法，参数，请求内容类型等
        new RxVolley.Builder()
                // 这里我加了一个静态类，host就是api的地址，直接在后面追加进行操作的地址就OK
                .url(ServerParams.host + "/order")
                .httpMethod(RxVolley.Method.POST)
                .contentType(RxVolley.ContentType.JSON)
                .params(params)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        System.out.println(t);
                        getData();
                        setEnabled(false);
                        finish();
                    }
                }) //响应回调
                .encoding("UTF-8") //编码格式，默认为utf-8
                .doTask();  //执行请求操作
    }
}
