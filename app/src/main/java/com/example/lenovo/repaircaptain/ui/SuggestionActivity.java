package com.example.lenovo.repaircaptain.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.entity.UserModel;
import com.example.lenovo.repaircaptain.utils.ServerParams;
import com.example.lenovo.repaircaptain.utils.ShareUtils;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import java.util.HashMap;

/**
 * Created by lenovo on 2018/4/29.
 */

public class SuggestionActivity extends BaseActivity implements View.OnClickListener {
    private static final int MAX_COUNT = 300;
    private EditText mEtContent = null;
    private TextView mTextCount = null;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_suggestion);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mTextCount = (TextView) findViewById(R.id.text_count);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        mEtContent.addTextChangedListener(new TextWatcher() { //对EditText进行监听
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTextCount.setText("剩余字数：" + (MAX_COUNT - editable.length()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String suggestion = btn_submit.getText().toString();
                String phone = ((EditText)findViewById(R.id.et_feedback_phone)).getText().toString();
                String id = ShareUtils.getString(this, "id_user", "");
                String url = ServerParams.host + "/feedback";
                if (!TextUtils.isEmpty(suggestion)) {
                    HashMap<String, Object> form = new HashMap<>();
                    form.put("id_user", id);
                    form.put("phone", phone);
                    form.put("content", suggestion);
                    HttpParams params = new HttpParams();
                    // 这里有两步，先gson转为json，再putjson到params里面
                    params.putJsonParams(new Gson().toJson(form));
                    // 使用了高级用法，其实是一样的，设置url，请求方法，参数，请求内容类型等
                    HttpCallback callback = new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(SuggestionActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                            System.out.println(t);
                            finish();
                        }
                    };
                    // 这里有两步，先gson转为json，再putjson到params里面
                    params.putJsonParams(new Gson().toJson(form));
                    // 使用了高级用法，其实是一样的，设置url，请求方法，参数，请求内容类型等
                    new RxVolley.Builder()
                            // 这里我加了一个静态类，host就是api的地址，直接在后面追加进行操作的地址就OK
                            .url(url)
                            .httpMethod(RxVolley.Method.PUT)
                            .contentType(RxVolley.ContentType.JSON)
                            .params(params)
                            .callback(callback) //响应回调
                            .encoding("UTF-8") //编码格式，默认为utf-8
                            .doTask();  //执行请求操作
                } else {
                    Toast.makeText(this, "反馈内容还没写哦！", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }
}


