package edu.hzuapps.androidworks.homeworks.net1314080903129;

/**
 * Created by Administrator on 2016/4/25.
 */
import java.io.Serializable;
import java.util.List;

/**
 * Created by dudon on 2016/1/14.
 */
public class Net1314080903129User implements Serializable {
    public static final int[] images = {R.drawable.a, R.drawable.net1314080903129b, R.drawable.net1314080903129c, R.drawable.d,
            R.drawable.net1314080903129e, R.drawable.net1314080903129f, R.drawable.net1314080903129g, R.drawable.net1314080903129h, R.drawable.i, R.drawable.net1314080903129j,
            R.drawable.net1314080903129k, R.drawable.net1314080903129l, R.drawable.net1314080903129m, R.drawable.n, R.drawable.net1314080903129o, R.drawable.p,
            R.drawable.net1314080903129q, R.drawable.net1314080903129r, R.drawable.s, R.drawable.net1314080903129t, R.drawable.net1314080903129u, R.drawable.net1314080903129v,
            R.drawable.net1314080903129w, R.drawable.net1314080903129x, R.drawable.net1314080903129y, R.drawable.net1314080903129z};

    private int image;
    private String name;
    private String message;
    private List<Message> chats;

    public Net1314080903129User(int image, String name, String message) {
        this.image = image;
        this.name = name;
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static int[] getImages() {
        return images;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public List<Message> getChats() {
        return chats;
    }

    public void setChats(List<Message> chats) {
        this.chats = chats;
    }


}
