package com.example.lenovo.repaircaptain.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by lenovo on 2018/3/14.
 * 工具的统一类
 */

public class UtilTools {

    //设置字体
        public static void setFont(Context mContext, TextView textView){
            Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
            textView.setTypeface(fontType);


        }




    //保存图片到ShareUtils
    public static void putImageToShare(Context mContext,ImageView imageView){

        //保存
        BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //1 将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //2利用Base64将我们的字节数组输出流转换成String
        byte [] byteArray = byStream.toByteArray();
        String imaString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        ShareUtils.putString(mContext,"image_title",imaString);
    }

    //读取图片
    public static void getImageToShare(Context mContext,ImageView imageView) {
        //1拿到String
        String imgString = ShareUtils.getString(mContext, "image_title", "");
        if (!imgString.equals("")) {
            //2.利用Base64将我们的字节数组输出流转换成String
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }

    }
}
