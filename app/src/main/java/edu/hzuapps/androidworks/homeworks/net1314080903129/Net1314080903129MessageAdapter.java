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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Net1314080903129MessageAdapter extends ArrayAdapter<Message> {

    private int resourceID;
    private Net1314080903129RoundRect net1314080903129RoundRectNet1314080903129 = new Net1314080903129RoundRect(100, 100, 50);

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    private int userImage;

    public Net1314080903129MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        resourceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.receiver_layout);
            viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.send_layout);
            viewHolder.left_image = (ImageView) view.findViewById(R.id.left_user);
            viewHolder.right_image = (ImageView) view.findViewById(R.id.right_user);
            viewHolder.left_text = (TextView) view.findViewById(R.id.white_message);
            viewHolder.right_text = (TextView) view.findViewById(R.id.green_message);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //判断消息发送方
        if (message.getType() == Message.TYPE_RECEIVED) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.left_text.setText(message.getContent());
            //设置图片
            Bitmap bitmap = net1314080903129RoundRectNet1314080903129.toRoundRect(getContext(), userImage);
            viewHolder.left_image.setImageBitmap(bitmap);

        } else if (message.getType() == Message.TYPE_SENT) {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.right_text.setText(message.getContent());
            //设置图片
            Bitmap bitmap = net1314080903129RoundRectNet1314080903129.toRoundRect(getContext(), R.drawable.net1314080903129me);
            viewHolder.right_image.setImageBitmap(bitmap);
        }

        return view;
    }

    class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        ImageView left_image;
        ImageView right_image;
        TextView left_text;
        TextView right_text;

    }
}