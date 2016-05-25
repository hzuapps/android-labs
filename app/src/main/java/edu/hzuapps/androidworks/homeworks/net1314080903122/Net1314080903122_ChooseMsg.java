package edu.hzuapps.androidworks.homeworks.net1314080903122;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import edu.hzuapps.androidworks.homeworks.net1314080903122.Fragment.Net1314080903122_Category;
import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_FestivalLab;
import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_Msg;

public class Net1314080903122_ChooseMsg extends AppCompatActivity {

    private ListView mLvMsgs;
    private FloatingActionButton mFabTodsend;
    private ArrayAdapter<Net1314080903122_Msg> mAdapter;
    private int mFestivalId;
    private LayoutInflater mInflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903122__choose_msg);


        mInflater = LayoutInflater.from(this);
        mFestivalId = getIntent().getIntExtra(Net1314080903122_Category.ID_FESTIVAL, -1);

        setTitle(Net1314080903122_FestivalLab.getInstance().getFestivalById(mFestivalId).getName());

        initViews();
        initEvent();


    }

    private void initEvent() {

        mFabTodsend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Net1314080903122_SendMsg.toActivity(Net1314080903122_ChooseMsg.this, mFestivalId, -1);
            }
        });
    }

    private void initViews() {

        mLvMsgs = (ListView) findViewById(R.id.id_lv_msgs);
        mFabTodsend = (FloatingActionButton) findViewById(R.id.id_fab_toSent);

        mLvMsgs.setAdapter(mAdapter = new ArrayAdapter<Net1314080903122_Msg>(this,-1,
                        Net1314080903122_FestivalLab.getInstance().getmMsgsByFestivalId(mFestivalId)){
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        if (convertView == null)
                        {
                            convertView = mInflater.inflate(R.layout.net1314080903122_item_msg,parent,false);
                        }
                        TextView content = (TextView) convertView.findViewById(R.id.id_tv_content);
                        Button toSend = (Button) convertView.findViewById(R.id.id_btn_toSend);

                        content.setText("    " + getItem(position).getContent());
                        toSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Net1314080903122_SendMsg.toActivity(Net1314080903122_ChooseMsg.this,mFestivalId,getItem(position).getId());
                            }
                        });
                        return convertView;
                    }
                }

        );



    }

}
