package edu.hzuapps.androidworks.homeworks.net1314080903122.bean;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/20.
 */
public class Net1314080903122_Festival {

    private  int id;
    private String name;
    private String desc;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Net1314080903122_Festival(int id, String name){
        this.id = id;
        this.name = name;
    }



}
