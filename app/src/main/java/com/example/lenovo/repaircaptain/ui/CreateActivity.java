package com.example.lenovo.repaircaptain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.repaircaptain.MainActivity;
import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.entity.ErrMsg;
import com.example.lenovo.repaircaptain.utils.ServerParams;
import com.example.lenovo.repaircaptain.utils.ShareUtils;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by lenovo on 2018/3/14.
 */

public class CreateActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_address;
    private EditText et_machine;
    private EditText et_description;
    private Spinner sp_time1;
    private Spinner sp_time2;
    private Spinner sp_level;
    private Button btnSubmit;
    private TextView tv_located;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_create);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.color_blue));
        initView();
    }

    private void initView() {
        et_name=(EditText)findViewById(R.id.et_name);
        et_address=(EditText)findViewById(R.id.et_address);
        et_machine=(EditText)findViewById(R.id.et_machine);
        et_description=(EditText)findViewById(R.id.et_description);
        sp_time1=(Spinner)findViewById(R.id.sp_time1);
        sp_time2=(Spinner)findViewById(R.id.sp_time2);
        sp_level=(Spinner)findViewById(R.id.sp_level);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
       tv_located=(TextView)findViewById(R.id.tv_located);
       tv_located.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                String name = et_name.getText().toString().trim();
                String address = et_address.getText().toString().trim();
                String machine = et_machine.getText().toString().trim();
                String description = et_description.getText().toString().trim();
                String time1 = sp_time1.getSelectedItem().toString().trim();
                String time2 = sp_time2.getSelectedItem().toString().trim();
                String level = sp_level.getSelectedItem().toString().trim();
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat();
                Date date = new Date(System.currentTimeMillis());
                String time_creation= simpleDateFormat.format(date);

                String url = ServerParams.host + "/order";
                if(!TextUtils.isEmpty(name)&
                        !TextUtils.isEmpty(address)&!TextUtils.isEmpty(machine)&
                        !TextUtils.isEmpty(description)&!TextUtils.isEmpty(time1)&
                        !TextUtils.isEmpty(time2)&!TextUtils.isEmpty(level)) {
                    // 新建一个map对象存储数据
                    HashMap<String, Object> form = new HashMap<>();
                    form.put("id",1);
                    form.put("id_user",ShareUtils.getString(this,"id_user","1"));
                    form.put("name_machine",machine);
                    form.put("address", address);
                    form.put("description",description);
                    form.put("time1",time1);
                    form.put("time2",time2);
                    form.put("level",level);
                    form.put("time_creation",time_creation);
                    HttpParams params = new HttpParams();
                    params.putJsonParams(new Gson().toJson(form));
                    HttpCallback callback = new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(CreateActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                            System.out.println(t);
                            finish();

                        }
                    };
                    new RxVolley.Builder()
                            // 这里我加了一个静态类，host就是api的地址，直接在后面追加进行操作的地址就OK
                            .url(url)
                            .httpMethod(RxVolley.Method.PUT)
                            .contentType(RxVolley.ContentType.JSON)
                            .params(params)
                            .callback(callback) //响应回调
                            .encoding("UTF-8") //编码格式，默认为utf-8
                            .doTask();  //执行请求操作
                } else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.tv_located:
                startActivity(new Intent(this,LocationActivity.class));
                break;
        }
    }



}
