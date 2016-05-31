package edu.hzuapps.androidworks.homeworks.com1314080901103.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPageAdapter extends PagerAdapter {

    private List<View> list_view;

    public ViewPageAdapter(List<View> list_view) {
        this.list_view = list_view;
    }

    @Override
    public int getCount() {
        return list_view.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list_view.get(position % list_view.size()), 0);
        return list_view.get(position % list_view.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 把 Object 强转为 View，然后将 view 从 ViewGroup 中清除
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }
}