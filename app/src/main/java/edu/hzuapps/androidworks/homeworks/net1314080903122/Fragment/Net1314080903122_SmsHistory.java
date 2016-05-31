package edu.hzuapps.androidworks.homeworks.net1314080903122.Fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import edu.hzuapps.androidworks.homeworks.net1314080903122.R;
import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_SendedMsg;
import edu.hzuapps.androidworks.homeworks.net1314080903122.db.Net1314080903122_SmsProvider;
import edu.hzuapps.androidworks.homeworks.net1314080903122.view.FlowLayout;

/**
 * Created by Administrator on 2016/5/29.
 */
public class Net1314080903122_SmsHistory extends ListFragment{
    private static final int  LOADER_ID = 1;
    private LayoutInflater mInflater;
    private CursorAdapter mCursorAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInflater = LayoutInflater.from(getActivity());
        initLoader();
        setupListAdapter();
    }

    private void setupListAdapter() {
        mCursorAdapter = new CursorAdapter(getActivity(),null,false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = mInflater.inflate(R.layout.net1314080903122_item_sended_msg, parent, false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView msg = (TextView) view.findViewById(R.id.id_tv_msg);
                FlowLayout fl = (FlowLayout) view.findViewById(R.id.id_fl_contacts);
                TextView fes = (TextView) view.findViewById(R.id.id_tv_fes);
                TextView date = (TextView) view.findViewById(R.id.id_tv_date);

                msg.setText(cursor.getString(cursor.getColumnIndex(Net1314080903122_SendedMsg.COLUMN_MSG)));
                fes.setText(cursor.getString(cursor.getColumnIndex(Net1314080903122_SendedMsg.COLUMN_FES_NAME)));
                long dateVal=cursor.getLong(cursor.getColumnIndex(Net1314080903122_SendedMsg.COLUMN_DATE));
                date.setText(parseDate(dateVal));
                String names = cursor.getString(cursor.getColumnIndex(Net1314080903122_SendedMsg.COLUMN_NAMES));
                if (TextUtils.isEmpty(names))
                {
                    return;
                }
                fl.removeAllViews();
                for (String name : names.split(":"))
                {
                    addTag(name,fl);
                }
            }
        };
        setListAdapter(mCursorAdapter);
    }

    DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String parseDate(long dateVal) {
        return df.format(dateVal);
    }



    private void addTag(String name, FlowLayout fl) {
        TextView tv = (TextView) mInflater.inflate(R.layout.net1314080903122_tag, fl, false);
        tv.setText(name);
        fl.addView(tv);
    }





    private void initLoader() {
        getLoaderManager().initLoader(LOADER_ID, null,new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader loader = new CursorLoader(getActivity(), Net1314080903122_SmsProvider.URI_SMS_ALL,null,null,null,null);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (loader.getId() == LOADER_ID)
            {
                mCursorAdapter.swapCursor(data);
            }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mCursorAdapter.swapCursor(null);

            }
        });
    }
}
