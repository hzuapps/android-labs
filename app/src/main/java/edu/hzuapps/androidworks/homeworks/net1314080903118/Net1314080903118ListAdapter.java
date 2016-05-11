package edu.hzuapps.androidworks.homeworks.net1314080903118;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidwork.R;

import java.util.List;

/**
 * ListView适配器
 * Created by RImpression on 2016/4/29 0029.
 */
public class Net1314080903118ListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Net1314080903118ArticleEntity> entities;
    private List<Integer> typeList;

    public Net1314080903118ListAdapter(Context context,List<Net1314080903118ArticleEntity> data) {
        this.mContext = context;
        this.entities = data;
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Object getItem(int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.net1314080903118_item_card,parent,false);
            holder = new ViewHolder();

            holder.tvDateList = (TextView) convertView.findViewById(R.id.tvDateList);
            holder.tvType1 = (TextView) convertView.findViewById(R.id.tvType1);
            holder.tvHPTitle = (TextView) convertView.findViewById(R.id.tvHPTitle);
            holder.tvUserName1 = (TextView) convertView.findViewById(R.id.tvUserName1);
            holder.tvGuideWord = (TextView) convertView.findViewById(R.id.tvGuideWord);
            holder.tvType2 = (TextView) convertView.findViewById(R.id.tvType2);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            holder.tvExcerpt = (TextView) convertView.findViewById(R.id.tvExcerpt);
            holder.tvType3 = (TextView) convertView.findViewById(R.id.tvType3);
            holder.tvQuestTitle = (TextView) convertView.findViewById(R.id.tvQuestionTitle);
            holder.tvAnswerTitle = (TextView) convertView.findViewById(R.id.tvAnswerTitle);
            holder.tvAnswerContent = (TextView) convertView.findViewById(R.id.tvAnserContent);
            holder.cardView1 = (LinearLayout) convertView.findViewById(R.id.cardView1);
            holder.cardView2 = (LinearLayout) convertView.findViewById(R.id.cardView2);
            holder.cardView3 = (LinearLayout) convertView.findViewById(R.id.cardView3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Net1314080903118ArticleEntity entity = entities.get(position);
        Log.i("listAdapter",entity.toString());
        //用于判断类型，1=短篇，2=长篇连载，3=问答
        typeList = entity.getType();
        holder.tvDateList.setText(entity.getDate());
        for (int i=0;i<typeList.size();i++) {
            if (typeList.get(i) == 3) {
                holder.cardView3.setVisibility(View.VISIBLE);
                holder.tvType3.setText("问答");
                holder.tvQuestTitle.setText(entity.getQuestion_title());
                holder.tvAnswerTitle.setText(entity.getAnswer_title());
                holder.tvAnswerContent.setText(entity.getAnswer_content());
                holder.cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,entity.getQuestion_title(),Toast.LENGTH_SHORT).show();
                        //intentView(entity.getQuestion_id(), QuestionActivity.class);
                    }
                });

            } else if (typeList.get(i) == 2) {
                holder.cardView2.setVisibility(View.VISIBLE);
                holder.tvType2.setText("连载");
                holder.tvTitle.setText(entity.getTitle());
                holder.tvUserName.setText(entity.getUser_name());
                holder.tvExcerpt.setText(entity.getExcerpt());
                holder.cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,entity.getTitle(),Toast.LENGTH_SHORT).show();
                        //intentView(entity.getId(), SerializeActivity.class);
                    }
                });

            } else if (typeList.get(i) == 1) {
                holder.cardView1.setVisibility(View.VISIBLE);
                holder.tvType1.setText("短篇");
                holder.tvHPTitle.setText(entity.getHp_title());
                holder.tvUserName1.setText(entity.getUser_name());
                holder.tvGuideWord.setText(entity.getGuide_word());
                holder.cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,entity.getHp_title(),Toast.LENGTH_SHORT).show();
                        //intentView(entity.getContent_id(), EssayActivity.class);
                    }
                });
            }
        }

        return convertView;
    }


    private void intentView(String id, Class<?> activityClass) {
        Intent intent = new Intent();
        intent.putExtra("ID",id);
        intent.setClass(mContext, activityClass);
        mContext.startActivity(intent);
    }

    private class ViewHolder {
         TextView tvType1,tvType2,tvType3;
         TextView tvHPTitle,tvTitle,tvQuestTitle;
         TextView tvUserName1,tvUserName,tvAnswerTitle;
         TextView tvGuideWord,tvExcerpt,tvAnswerContent;
         LinearLayout cardView1,cardView2,cardView3;
         TextView tvDateList;
    }
}
