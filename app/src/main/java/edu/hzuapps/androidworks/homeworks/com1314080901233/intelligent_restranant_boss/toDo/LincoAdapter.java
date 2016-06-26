package com.example.intelligent_restranant_boss.toDo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

public class LincoAdapter extends BaseAdapter {     //这个adapter是操作一组LL的集合,LL的数据是先填好的,没有对应适配数据的过程
    private List<LinearLayout>mData;
    private LayoutInflater mLF;
    Context mcontext;
    public LincoAdapter(Context context, List<LinearLayout> mData){
        this.mData=mData;
        mLF=LayoutInflater.from(context);
        mcontext=context;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout mfl=new FrameLayout(mcontext);
        if (convertView==null){
            if(mData.get(position).getParent()!=null)
                ((ViewGroup)(mData.get(position).getParent())).removeAllViews();
            mfl.addView(mData.get(position));
            convertView=mfl;
        }
        return convertView;

    }
}

