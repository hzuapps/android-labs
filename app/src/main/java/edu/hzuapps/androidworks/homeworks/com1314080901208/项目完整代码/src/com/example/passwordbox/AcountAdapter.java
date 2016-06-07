package com.example.passwordbox;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AcountAdapter extends ArrayAdapter<AcountData> {
	private int resourceId;

	public AcountAdapter(Context context, int textViewResourceId,
			List<AcountData> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AcountData acountData = getItem(position); // 获取当前项的Fruit实例
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView acountId = (TextView) view.findViewById(R.id.acountId);
		TextView acountLabel = (TextView) view.findViewById(R.id.acountLabel);
		acountId.setText(String.valueOf(acountData.getAcountId()));
		acountLabel.setText(acountData.getAcountLabel());
		return view;
	}
}
