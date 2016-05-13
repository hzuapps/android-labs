package edu.hzuapps.androidworks.homeworks.net1314080903129;

/**
 * Created by Administrator on 2016/4/25.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dudon on 2016/1/14.
 */
public class Net1314080903129UserAdapter extends ArrayAdapter<Net1314080903129User> {

    private int resourceID;
    private Net1314080903129RoundRect net1314080903129RoundRectNet1314080903129 = new Net1314080903129RoundRect(160, 160, 80);

    public Net1314080903129UserAdapter(Context context, int resource, List<Net1314080903129User> objects) {
        super(context, resource, objects);
        resourceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Net1314080903129User user = getItem(position);
        View view;
        ViewHolder viewHolder;
        //缂撳瓨鏈哄埗
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, null);
            viewHolder = new ViewHolder();
            viewHolder.userImage = (ImageView) view.findViewById(R.id.user_image);
            viewHolder.userName = (TextView) view.findViewById(R.id.user_name);
            viewHolder.userMessage = (TextView) view.findViewById(R.id.user_massage);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //鍦嗗舰澶村儚
        Bitmap bitmap = net1314080903129RoundRectNet1314080903129.toRoundRect(getContext(), user.getImage());
        viewHolder.userImage.setImageBitmap(bitmap);
        viewHolder.userName.setText(user.getName());
        viewHolder.userMessage.setText(user.getMessage());

        return view;
    }

    class ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView userMessage;
    }
}
