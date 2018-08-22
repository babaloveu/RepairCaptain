package com.example.lenovo.repaircaptain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.repaircaptain.MainActivity;
import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.entity.ErrMsg;
import com.example.lenovo.repaircaptain.utils.ServerParams;
import com.example.lenovo.repaircaptain.utils.ShareUtils;
import com.example.lenovo.repaircaptain.view.CustomDialog;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.HashMap;

/**
 * Created by lenovo on 2018/3/16.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView btn_register;
    private EditText et_name;
    private EditText et_npass;
    private Button btnLogin;
    private CheckBox keep_pass;
    private TextView tv_forget;
    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }


    private void initView() {
        btn_register = (TextView) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_npass = (EditText) findViewById(R.id.et_npass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        keep_pass = (CheckBox) findViewById(R.id.keep_pass);
//        tv_forget = (TextView) findViewById(R.id.tv_forget);
//        tv_forget.setOnClickListener(this);

        //初始化dialog
        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        //设置屏幕外点击无效
        dialog.setCancelable(false);
        //设置选中状态
        boolean isCheck = ShareUtils.getBoolean(this, "keeppass", false);
        keep_pass.setChecked(isCheck);

        et_name.setText(ShareUtils.getString(this, "name", ""));
        et_npass.setText(ShareUtils.getString(this, "password", ""));
    }

    public void login() {
        // 新建一个map对象存储数据
        HashMap<String, Object> form = new HashMap<>();
        form.put("account", et_name.getText().toString());
        form.put("password", et_npass.getText().toString());
        HttpParams params = new HttpParams();
        // 这里有两步，先gson转为json，再putjson到params里面
        params.putJsonParams(new Gson().toJson(form));
        // 使用了高级用法，其实是一样的，设置url，请求方法，参数，请求内容类型等
        new RxVolley.Builder()
                // 这里我加了一个静态类，host就是api的地址，直接在后面追加进行操作的地址就OK
                .url(ServerParams.host + "/login")
                .httpMethod(RxVolley.Method.POST)
                .contentType(RxVolley.ContentType.JSON)
                .params(params)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);

                        ErrMsg msg = new Gson().fromJson(t, ErrMsg.class);
                        System.out.println(t);
                        ShareUtils.putString(getApplicationContext(), "id_user", msg.data);
                        dialog.dismiss();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }) //响应回调
                .encoding("UTF-8") //编码格式，默认为utf-8
                .doTask();  //执行请求操作
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
//            case R.id.tv_forget:
//                startActivity(new Intent(this, ForgetPassActivity.class));
//                break;
            case R.id.btnLogin:
                login();
                dialog.show();
//                //1获取用户名密码输入框的值
//                String name = et_name.getText().toString().trim();
//                String password = et_npass.getText().toString().trim();
//                //2判断是否为空
//                if(!TextUtils.isEmpty(name)&!TextUtils.isEmpty(password)){
//                    dialog.show();
//                    //登录
//                    final MyUser user = new MyUser();
//                    user.setUsername(name);
//                    user.setPassword(password);
//                    user.login(new SaveListener<MyUser>() {
//                        @Override
//                        public void done(MyUser myUser, BmobException e) {
//                           dialog.dismiss();
//                            //判断结果
//                            if (e==null){
//                                //判断邮箱是否验证
//                                if(user.getEmailVerified()){
//                                    //跳转
//                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                    finish();
//                                }else{
//                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证",Toast.LENGTH_SHORT).show();
//                                }
//
//                            }else{
//                                Toast.makeText(LoginActivity.this,"登录失败："+e.toString(),Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                }else{
//                    Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
//                }
            break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        ;
        //保存状态
        ShareUtils.putBoolean(this, "keeppass", keep_pass.isChecked());

        //判断是否勾选记住密码
        if (keep_pass.isChecked()) {
            //勾选了 那就保存用户名密码
            ShareUtils.putString(this, "name", et_name.getText().toString().trim());
            ShareUtils.putString(this, "password", et_npass.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }


    }


}
