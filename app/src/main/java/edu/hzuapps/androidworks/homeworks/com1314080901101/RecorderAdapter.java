package edu.hzuapps.androidworks.homework.com1314080901101;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.hzuapps.androidworks.homework.com1314080901101.MainActivity.Recorder;

public class RecorderAdapter extends ArrayAdapter<Recorder> {
    private List<Recorder> mDatas;
    private Context mContext;

    private int mMinItemWidth;
    private int mMaxItemWidth;
    private LayoutInflater mInflater;

    public RecorderAdapter(Context context, List<Recorder> datas) {
        super(context, -1, datas);
        mContext = context;
        mDatas = datas;

        //获取屏幕的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItemWidth = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);

        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.com1314080901101_item_recorder, parent, false);
            holder = new ViewHolder();
            holder.seconds = (TextView) convertView.findViewById(R.id.recorder_time);
            holder.length = convertView.findViewById(R.id.recorder_length);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.seconds.setText(Math.round(getItem(position).time) + "\"");
        ViewGroup.LayoutParams lp = holder.length.getLayoutParams();
        lp.width = (int) (mMinItemWidth + (mMaxItemWidth / 60f) * getItem(position).time);

        return convertView;
    }

    private class ViewHolder {
        TextView seconds; //时间
        View length; //长度
    }
}
