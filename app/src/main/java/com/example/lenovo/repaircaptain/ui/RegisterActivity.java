package com.example.lenovo.repaircaptain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import com.kymjs.rxvolley.http.VolleyError;

import java.util.HashMap;

/**
 * Created by lenovo on 2018/3/16.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_register_phone;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_pass;
    private EditText et_password;
    private EditText et_email;
    private Button btnRegistered;

    //性别
    private boolean isGender = true;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    public void initView(){
        et_user =(EditText)findViewById(R.id.et_user);
        et_register_phone =(EditText)findViewById(R.id.et_register_phone);
        et_desc =(EditText)findViewById(R.id.et_desc);
        mRadioGroup =(RadioGroup)findViewById(R.id.mRadioGroup);
        et_pass =(EditText)findViewById(R.id.et_pass);
        et_password =(EditText)findViewById(R.id.et_password);
        et_email =(EditText)findViewById(R.id.et_email);
        btnRegistered = (Button)findViewById(R.id.btnRegistered);
        btnRegistered.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
    case R.id.btnRegistered:
        //获取到输入框的值
        String name = et_user.getText().toString().trim();
        String age = et_register_phone.getText().toString().trim();
        String desc = et_desc.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        //判断是否为空
        if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(age)&
            !TextUtils.isEmpty(pass)&!TextUtils.isEmpty(password)&
            !TextUtils.isEmpty(email)){
            //判断两次输入的面膜是否一致
            if(pass.equals(password)) {
                register();
//                //先把性别判断一下
//                mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        if (checkedId == R.id.rb_boy) {
//                            isGender = true;
//                        } else if (checkedId == R.id.rb_girl) {
//                            isGender = false;
//                        }
//                    }
//                });
//                //判断简介是否为空
//                if (TextUtils.isEmpty(desc)) {
//                    desc = "这个人很懒什么都没有留下";
//                }
//                //注册
//                MyUser user = new MyUser();
//                user.setUsername(name);
//                user.setPassword(password);
//                user.setEmail(email);
//                user.setAge(Integer.parseInt(age));
//                user.setSex(isGender);
//                user.setDesc(desc);
//                //注意：不能用save方法进行注册
//                user.signUp(new SaveListener<MyUser>() {
//                    @Override
//                    public void done(MyUser myUser, BmobException e) {
//                        if (e == null) {
//                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                            finish();
//                        } else {
//                            Toast.makeText(RegisterActivity.this, "注册失败：" + e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }else{
                Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();

            }
    }else{
            Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
        }

        break;
}
    }
    public void register() {
        //获取到输入框的值
        String name = et_user.getText().toString().trim();
        String desc = et_desc.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String phone1 = et_register_phone.getText().toString().trim();
        //先把性别判断一下
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_boy) {
                    isGender = true;
                } else if (checkedId == R.id.rb_girl) {
                    isGender = false;
                }
            }
        });
        //判断简介是否为空
        if (TextUtils.isEmpty(desc)) {
            desc = "这个人很懒什么都没有留下";
        }
        // 新建一个map对象存储数据
        HashMap<String, Object> form = new HashMap<>();
        form.put("name", name);
        form.put("password", password);
        form.put("email", email);
        form.put("phone1", phone1);
        form.put("phone2", desc);
        form.put("sex", isGender);
        HttpParams params = new HttpParams();
        // 这里有两步，先gson转为json，再putjson到params里面
        params.putJsonParams(new Gson().toJson(form));
        // 使用了高级用法，其实是一样的，设置url，请求方法，参数，请求内容类型等
        new RxVolley.Builder()
                // 这里我加了一个静态类，host就是api的地址，直接在后面追加进行操作的地址就OK
                .url(ServerParams.host + "/login")
                .httpMethod(RxVolley.Method.PUT)
                .contentType(RxVolley.ContentType.JSON)
                .params(params)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        ErrMsg msg = new Gson().fromJson(t, ErrMsg.class);
                        System.out.println(t);
                        ShareUtils.putString(getApplicationContext(), "id_user", msg.data);
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                    @Override
                    public void onFailure(VolleyError error) {
                        super.onFailure(error);
                        Toast.makeText(RegisterActivity.this, "注册失败：" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) //响应回调
                .encoding("UTF-8") //编码格式，默认为utf-8
                .doTask();  //执行请求操作
    }
}
