package com.example.intelligent_restranant_boss.toDo;

/**
 * Created by Linco_325 on 2016/4/11.
 */
//一个记事内容对象
public class Single_Note_Object {
    private String single_note_title;   //标题
    private String single_note_content; //内容
    private String single_Note_time;    //时间
    private int note_no_;   //编号
    //各种构造方法
    public Single_Note_Object(int no_, String title, String content, String time){
        this.note_no_ =no_;
        this.single_note_title =title;
        this.single_note_content=content;
        this.single_Note_time =time;
    }
    public Single_Note_Object(String title, String content, String time){
        this.single_note_title =title;
        this.single_note_content =content;
        this.single_Note_time =time;
    }
    public Single_Note_Object(int no_, String title, String time){
        this.note_no_ =no_;
        this.single_note_title =title;
        this.single_Note_time =time;
    }
    public Single_Note_Object(String title, String content){
        this.single_note_title =title;
        this.single_note_content =content;
    }
    public int get_Note_no_() {
        return note_no_;
    }
    public String get_Note_Title() {
        return single_note_title;
    }
    public String get_Note_time() {
        return single_Note_time;
    }
}
