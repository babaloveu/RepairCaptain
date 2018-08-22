package com.example.lenovo.repaircaptain.entity;

/**
 * Created by lenovo on 2018/3/19.
 */

public class WeChatData {
    //标题
    private String title;
    //出处
    private String source;
    //图片的url
    private String imgUrl;
    //新闻的地址
    private String newsURL;

    public  String getTitle() {
        return title;
    }
    public void  setTitle(String title) {
        this.title = title;
    }
    public String getSource() {
        return source;
    }
    public void   setSource(String source) {
        this.source = source;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void   setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getNewsURL() {
        return newsURL;
    }
    public void   setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }
}
