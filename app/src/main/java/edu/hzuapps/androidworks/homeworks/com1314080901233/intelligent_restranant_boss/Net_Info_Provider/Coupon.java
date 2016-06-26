package com.example.intelligent_restranant_boss.Net_Info_Provider;

import cn.bmob.v3.BmobObject;

/**
 * Created by Linco_325 on 2016/6/9.
 */
public class Coupon extends BmobObject {
    //继承自BmobObject,实现其方法
    private String pic_url;
    private String title;
    private String content;

    public Coupon(String pic_url, String title, String content){
        this.pic_url = pic_url;
        this.title = title;
        this.content = content;

    }
    public Coupon(){} //学聪明了
    public String getPic_url() {
        return pic_url;
    }
    public void setPic_url(String str1){
        this.pic_url =str1;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String str3){
        this.content =str3;
    }

}
