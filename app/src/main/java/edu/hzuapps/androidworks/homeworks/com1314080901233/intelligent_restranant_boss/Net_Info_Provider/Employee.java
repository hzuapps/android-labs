package com.example.intelligent_restranant_boss.Net_Info_Provider;

import cn.bmob.v3.BmobObject;

/**
 * Created by Linco_325 on 2016/6/9.
 */
public class Employee extends BmobObject {
    //继承自BmobObject,实现其方法
    private String head_url;
    private String name;
    private String job;
    private String phone;
    private int pay;

    public Employee(String name, String job, String phone,int pay){
        this.name=name;
        this.job=job;
        this.pay = pay;
        this.phone=phone;
    }
    public Employee(){}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getPay() {
        return pay;
    }
    public void setPay(int pay) {
        this.pay = pay;
    }

}
