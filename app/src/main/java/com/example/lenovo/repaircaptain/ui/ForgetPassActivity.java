package com.example.lenovo.repaircaptain.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by lenovo on 2018/3/18.
 * 忘记/重置密码
 */

public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_forget_password;
    private EditText et_email;
    private EditText et_now;
    private EditText et_new;
    private EditText et_new_password;
    private Button btn_update_password;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    public void initView(){
        btn_forget_password =findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
        et_email = (EditText) findViewById(R.id.et_email);
        et_now = (EditText) findViewById(R.id.et_now);
        et_new = (EditText) findViewById(R.id.et_new);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
       btn_update_password = (Button) findViewById(R.id.btn_update_password);
       btn_update_password.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_forget_password:
                //1.获取输入框的内容
               final String email = et_email.getText().toString().trim();
                //2.判断是否为空
                if(!TextUtils.isEmpty(email)){
                    //3.发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ForgetPassActivity.this,"请求成功邮件已发送至" + email ,Toast.LENGTH_SHORT);
                                finish();
                            }else{
                                Toast.makeText(ForgetPassActivity.this,"请求失败"+e,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(ForgetPassActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_update_password:
                //1.获取三个输入框的内容
                 String nowpass = et_now.getText().toString().trim();
                 String newpass = et_new.getText().toString().trim();
                 String againpass = et_new_password.getText().toString().trim();
                //2.判断是否为空
                if(!TextUtils.isEmpty(nowpass)&!TextUtils.isEmpty(newpass)
                        &!TextUtils.isEmpty(againpass)) {
                    //3判断新旧密码是否一致
                    if(newpass.equals(againpass)) {
                        //重置密码
                        MyUser.updateCurrentUserPassword(nowpass, newpass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgetPassActivity.this, "修改成功，可以用新密码进行登录啦", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPassActivity.this, "修改失败" + e, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(ForgetPassActivity.this, "两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ForgetPassActivity.this, "输入框不能为空" ,Toast.LENGTH_SHORT).show();
                }
                break;



        }


    }
}
