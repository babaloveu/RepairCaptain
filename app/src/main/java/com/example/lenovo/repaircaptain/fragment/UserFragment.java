package com.example.lenovo.repaircaptain.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.repaircaptain.R;
import com.example.lenovo.repaircaptain.entity.MyUser;
import com.example.lenovo.repaircaptain.entity.UserModel;
import com.example.lenovo.repaircaptain.ui.LoginActivity;
import com.example.lenovo.repaircaptain.utils.L;
import com.example.lenovo.repaircaptain.utils.ServerParams;
import com.example.lenovo.repaircaptain.utils.ShareUtils;
import com.example.lenovo.repaircaptain.utils.UtilTools;
import com.example.lenovo.repaircaptain.view.CustomDialog;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;

import java.io.File;
import java.util.HashMap;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lenovo on 2018/3/14.
 */

public class UserFragment extends Fragment implements View.OnClickListener {
    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_user_phone;
    private EditText et_user_desc;
    private Button btn_update_ok;
    //圆形头像
    private CircleImageView profile_image;
    private Dialog dialog;
    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;
    public View mView;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        this.mView = view;
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = (TextView) view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);

        et_username = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_user_phone = view.findViewById(R.id.et_user_phone);
        et_user_desc = view.findViewById(R.id.et_user_desc);

        btn_update_ok = (Button) view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        UtilTools.getImageToShare(getActivity(), profile_image);
        //初始化dialog
        dialog = new CustomDialog(getActivity(), 0, 0,
                R.layout.dialog_photo, R.style.Theme_dialog, Gravity.BOTTOM, R.style.pop_anim_style);
        //设置屏幕外点击无效
        dialog.setCancelable(false);
        btn_camera = dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);


        //默认是不可编辑的

        et_username.setEnabled(false);
        et_sex.setEnabled(false);
        et_user_phone.setEnabled(false);
        et_user_desc.setEnabled(false);

        //设置具体的值
        HttpParams httpParams = new HttpParams();
        httpParams.put("id", ShareUtils.getString(getActivity().getApplicationContext(), "id_user", ""));
        HttpCallback httpCallback = new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                UserModel userInfo = new Gson().fromJson(t, UserModel.class);
                et_username.setText(userInfo.name);
                et_user_phone.setText(userInfo.phone1);
                et_sex.setText(userInfo.sex == 1 ? "男" : "女");
                et_user_desc.setText(userInfo.phone2);
                mView.postInvalidate();
            }
        };
        new RxVolley.Builder()
                .url(ServerParams.host + "/user")
                .httpMethod(RxVolley.Method.GET)
                .params(httpParams)
                .callback(httpCallback)
                .doTask();
//        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
//        et_username.setText(userInfo.getUsername());
//        et_age.setText(userInfo.getAge() + "");
//        et_sex.setText(userInfo.isSex() ? "男" : "女");
//        et_user_desc.setText(userInfo.getDesc());
    }

    //控制焦点
    private void setEnabled(boolean is) {
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_user_phone.setEnabled(is);
        et_user_desc.setEnabled(is);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_user:
                //退出登录
                MyUser.logOut();
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.edit_user:
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //1.拿到输入框的值
                String username = et_username.getText().toString();
                String age = et_user_phone.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_user_desc.getText().toString();
                //2.判断是否为空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)) {
                    updateUser();
//                    //3.更新属性
//                    MyUser user = new MyUser();
//                    user.setUsername(username);
//                    user.setAge(Integer.parseInt(age));
//                    //性别
//                    if (sex.equals(getString(R.string.text_boy))) {
//                        user.setSex(true);
//                    } else {
//                        user.setSex(false);
//                    }
//                    //简介
//                    if (!TextUtils.isEmpty(desc)) {
//                        user.setDesc(desc);
//                    } else {
//                        user.setDesc("这个人很懒，什么都没有留下");
//                    }
//                    BmobUser bmobUser = BmobUser.getCurrentUser();
//                    user.update(bmobUser.getObjectId(), new UpdateListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if (e == null) {
//                                //修改成功
//                                setEnabled(false);
//                                btn_update_ok.setVisibility(View.GONE);
//                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
        }
    }
    private void updateUser() {
        //拿到输入框的值
        String username = et_username.getText().toString();
        String phone1 = et_user_phone.getText().toString();
        String phone2 = et_user_desc.getText().toString();
        //性别
        boolean gender = et_sex.getText().toString().equals(getString(R.string.text_boy));
        //简介
        phone2 = !TextUtils.isEmpty(phone2) ? phone2 : "这个人很懒，什么都没有留下";
        // 新建一个map对象存储数据
        HashMap<String, Object> form = new HashMap<>();
        form.put("id", ShareUtils.getString(getActivity().getApplication(), "id_user", ""));
        form.put("name", username);
        form.put("phone1", phone1);
        form.put("phone2", phone2);
        form.put("sex", gender);
        form.put("email", "");
        HttpParams params = new HttpParams();
        // 这里有两步，先gson转为json，再putjson到params里面
        params.putJsonParams(new Gson().toJson(form));
        // 使用了高级用法，其实是一样的，设置url，请求方法，参数，请求内容类型等
        new RxVolley.Builder()
                // 这里我加了一个静态类，host就是api的地址，直接在后面追加进行操作的地址就OK
                .url(ServerParams.host + "/user")
                .httpMethod(RxVolley.Method.POST)
                .contentType(RxVolley.ContentType.JSON)
                .params(params)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        //修改成功
                        setEnabled(false);
                        btn_update_ok.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(VolleyError error) {
                        super.onFailure(error);
                        Toast.makeText(getActivity(), "修改失败：" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) //响应回调
                .encoding("UTF-8") //编码格式，默认为utf-8
                .doTask();  //执行请求操作
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File templeFile = null;

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相册返回数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机返回数据
                case CAMERA_REQUEST_CODE:
                    templeFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(templeFile));
                    break;

                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //设置了图片，就把原先的图片删除
                        if (templeFile != null) {
                            templeFile.delete();
                        }
                    }
                    break;
            }
        }

    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectx", 1);
        intent.putExtra("aspecty", 1);
        //裁剪图片的质量
        intent.putExtra("outputx", 320);
        intent.putExtra("outputy", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);


    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);

        }

    }


    public void onDestroy() {
        super.onDestroy();
        //保存
        UtilTools.putImageToShare(getActivity(), profile_image);
    }


}
