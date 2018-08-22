package com.example.lenovo.repaircaptain.entity;

/**
 * Created by lenovo on 2018/5/1.
 */

public class HomeList {
    private String name;
    private int imageId;
    private String intro;
    private int rating;
    public HomeList(String name,int imageId,String intro,int rating){
        this.name =name;
        this.imageId =imageId;
        this.intro =intro;
        this.rating =rating;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
    public String getIntro(){
        return intro;
    }
    public int getRating(){
        return rating;
    }
}
