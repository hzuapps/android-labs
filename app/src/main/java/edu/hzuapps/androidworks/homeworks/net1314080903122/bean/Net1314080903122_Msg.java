package edu.hzuapps.androidworks.homeworks.net1314080903122.bean;

/**
 * Created by Administrator on 2016/5/20.
 */
public class Net1314080903122_Msg {
    private int id;
    private int festivalId;
    private String content;

    public Net1314080903122_Msg(int id, int festivalId, String content) {
        this.id = id;
        this.festivalId = festivalId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(int festivalId) {
        this.festivalId = festivalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
