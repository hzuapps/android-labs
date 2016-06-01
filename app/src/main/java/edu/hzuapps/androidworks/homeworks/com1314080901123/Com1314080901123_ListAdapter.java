package edu.hzuapps.androidworks.homeworks.com1314080901123;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LK on 2016/5/19.
 */
public class Com1314080901123_ListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<String[] > strList ;

    public Com1314080901123_ListAdapter(Context context, List<String[]> strList) {
        inflater = LayoutInflater.from(context);
        this.strList = strList;
    }

    @Override
    public int getCount() {
        return strList.size();
    }

    @Override
    public Object getItem(int position) {
        return strList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;

        if(convertView == null)
        {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item_com1314080901123, null);
            vh.tvStyle = (TextView) convertView.findViewById(R.id.buyStyle);
            vh.tvMoney = (TextView) convertView.findViewById(R.id.money);
            vh.tvDate = (TextView) convertView.findViewById(R.id.buyDate);

            convertView.setTag(vh);
        }else
        {
            vh = (ViewHolder) convertView.getTag();
        }

        //获取单条记录的相关信息
        String[] strs = strList.get(position);
        vh.tvStyle.setText(strs[0]);
        vh.tvMoney.setText(strs[1]);
        vh.tvDate.setText(strs[2]);


        return convertView;
    }
    class ViewHolder {
        TextView tvStyle;
        TextView tvDate;
        TextView tvMoney;
    }
}
