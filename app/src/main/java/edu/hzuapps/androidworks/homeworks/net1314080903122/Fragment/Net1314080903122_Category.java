package edu.hzuapps.androidworks.homeworks.net1314080903122.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import edu.hzuapps.androidworks.homeworks.net1314080903122.Net1314080903122_ChooseMsg;
import edu.hzuapps.androidworks.homeworks.net1314080903122.R;
import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_Festival;
import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_FestivalLab;

/**
 * Created by Administrator on 2016/5/20.
 */
public class Net1314080903122_Category extends Fragment{
    public static final String ID_FESTIVAL = "festival_id";
    private GridView mGridView;
    private ArrayAdapter<Net1314080903122_Festival> mAdapter;
    private LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.net1314080903122_category, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());
        mGridView = (GridView) view.findViewById(R.id.id_gv_festival_category);
        mGridView.setAdapter(mAdapter = new ArrayAdapter<Net1314080903122_Festival>(getActivity(), -1, Net1314080903122_FestivalLab.getInstance().getmFestivals()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.net1314080903122_item_fes, parent, false);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.id_tv_festival_name);
                tv.setText(getItem(position).getName());
                return convertView;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Net1314080903122_ChooseMsg.class);
                intent.putExtra(ID_FESTIVAL, mAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });


    }
}
