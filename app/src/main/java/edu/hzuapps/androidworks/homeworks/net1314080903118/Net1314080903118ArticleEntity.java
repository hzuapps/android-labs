package edu.hzuapps.androidworks.homeworks.net1314080903118;

import java.util.ArrayList;
import java.util.List;

/**阅读文章实体类
 * Created by RImpression on 2016/4/29 0029.
 */
public class Net1314080903118ArticleEntity {

    private String date;
    private List<String> time = new ArrayList<>();
    private List<Integer> type = new ArrayList<>() ;

    private String question_id;
    private String question_title;
    private String answer_title;
    private String answer_content;
    private String question_makettime;

    private String content_id;
    private String hp_title;
    private String guide_word;
    private boolean has_audio;


    private String id;
    private String serial_id;
    private String number;
    private String title;
    private String excerpt;
    private String read_num;
    private String maketime;

    private String user_id;
    private String user_name;
    private String web_url;
    private String wb_name;
    private String desc;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time.add(time);
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public List<Integer> getType() {
        return type;
    }

    public void setType(int type) {
        this.type.add(type);
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getAnswer_title() {
        return answer_title;
    }

    public void setAnswer_title(String answer_title) {
        this.answer_title = answer_title;
    }

    public String getQuestion_makettime() {
        return question_makettime;
    }

    public void setQuestion_makettime(String question_makettime) {
        this.question_makettime = question_makettime;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getGuide_word() {
        return guide_word;
    }

    public void setGuide_word(String guide_word) {
        this.guide_word = guide_word;
    }

    public String getHp_title() {
        return hp_title;
    }

    public void setHp_title(String hp_title) {
        this.hp_title = hp_title;
    }

    public boolean isHas_audio() {
        return has_audio;
    }

    public void setHas_audio(boolean has_audio) {
        this.has_audio = has_audio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getMaketime() {
        return maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getWb_name() {
        return wb_name;
    }

    public void setWb_name(String wb_name) {
        this.wb_name = wb_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
