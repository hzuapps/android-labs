package com.example.intelligent_restranant_boss.budget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.intelligent_restranant_boss.FrameActivity;
import com.example.intelligent_restranant_boss.MyActivity;
import com.example.intelligent_restranant_boss.MyPaymentActivity;
import com.example.intelligent_restranant_boss.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyBudgetActivity extends Activity implements OnClickListener {

    private TextView tv_date;
    private int index = 0;
    private TextView imv_left, imv_right;
    private GridView gridView = null;
    private DBOperation dbOperation;
    private TextView tv_total_month;
    private TextView tv_total_year;
    private static String year;
    private static String month;
    private DateSelectorAdapter mAdapter;
    private Button btn_left_header;
    private TextView tv_head_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_budget);

        findViewById();
        initTitle();
        tv_date.setText(DateUtil.getMonth(index));

        dbOperation = new DBOperation(getApplicationContext());
        dbOperation.openOrCreateDatebase();

        mAdapter = new DateSelectorAdapter(getApplicationContext(), index);
        gridView.setAdapter(mAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // dbOperation = new DBOperation(getApplicationContext());
        // dbOperation.openOrCreateDatebase();
        gridView.setAdapter(new DateSelectorAdapter(getApplicationContext(),
                index));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_left_header:

                Intent intent_back = new Intent(MyBudgetActivity.this,
                        FrameActivity.class);
                intent_back.putExtra("intent", "my");
                startActivity(intent_back);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;

            case R.id.left_img:
                index--;
                tv_date.setText(DateUtil.getMonth(index));
                gridView.setAdapter(new DateSelectorAdapter(
                        getApplicationContext(), index));
                break;
            case R.id.right_img:
                index++;

                tv_date.setText(DateUtil.getMonth(index));
                gridView.setAdapter(new DateSelectorAdapter(
                        getApplicationContext(), index));
                break;

            case R.id.tv_month:
                // dbOperation.close();

                Intent intent = new Intent(getApplicationContext(),
                        MonthBudgets.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                startActivity(intent);
                break;

            default:
                break;
        }

    }

    class DateSelectorAdapter extends BaseAdapter {
        private Context context = null;
        private Resources r;
        private int year = 0;
        private int month = 0;
        private int currentyear = 0;
        private int currentmonth = 0;
        private int currentday = 0;
        private String[] content;
        private String[] data;

        private int total_month = 0;

        public DateSelectorAdapter(Context context, int i) {
            this.context = context;
            r = context.getResources();
            monthChanged(i);
        }

        public void monthChanged(int offset) {
            Calendar now = new GregorianCalendar();
            year = now.get(Calendar.YEAR);
            month = now.get(Calendar.MONTH);
            if (offset == 0) {
                currentyear = year;
                currentmonth = month;
                currentday = now.get(Calendar.DATE);
            }
            int newMonth = month + offset;
            if (newMonth < 0) {
                int tempMonth = newMonth;
                for (; tempMonth < 0; tempMonth = tempMonth + 12) {
                    month = tempMonth + 12;
                    year = year - 1;
                }
            } else if (newMonth >= 12) {
                int tempMonth = newMonth;
                for (; tempMonth >= 12; tempMonth = tempMonth - 12) {
                    month = tempMonth - 12;
                    year = year + 1;
                }
            } else {
                month = newMonth;
            }
            int contentlength = getContentLength();
            content = new String[contentlength];
            bindCalendar();
        }

        private int getContentLength() {
            Calendar c = new GregorianCalendar(year, month, 1);
            int daysOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            int before = dayOfWeek - 1;
            c.set(Calendar.DATE, daysOfMonth);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            int behind = 7 - dayOfWeek;
            return daysOfMonth + before + behind;
        }

        private void bindCalendar() {
            Calendar calendar = new GregorianCalendar(year, month, 1);
            int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DATE, -1 * (dayofweek - 1));
            int i = 0;
            for (; calendar.get(Calendar.YEAR) < year
                    || (calendar.get(Calendar.YEAR) == year && calendar
                    .get(Calendar.MONTH) <= month); i++) {
                content[i] = calendar.get(Calendar.YEAR) + "-"
                        + calendar.get(Calendar.MONTH) + "-"
                        + calendar.get(Calendar.DATE);
                calendar.add(Calendar.DATE, 1);
            }
            dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
            for (; dayofweek != 1 && dayofweek <= 7; dayofweek++, i++) {
                content[i] = calendar.get(Calendar.YEAR) + "-"
                        + calendar.get(Calendar.MONTH) + "-"
                        + calendar.get(Calendar.DATE);
                calendar.add(Calendar.DATE, 1);
            }
            data = new String[content.length];
            System.arraycopy(content, 0, data, 0, content.length);
        }

        @Override
        public int getCount() {
            return 35;
        }

        @Override
        public Object getItem(int position) {
            if (data != null) {
                return data[position];
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            LayoutInflater li = LayoutInflater.from(context);
            final LinearLayout ll = (LinearLayout) li.inflate(
                    R.layout.my_budget_grid_view_item, null);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 120);
            ll.setLayoutParams(lp);
            TextView tv = (TextView) ll.findViewById(R.id.grid_item_tv);
            TextView price = (TextView) ll.findViewById(R.id.grid_item_price);
            String date = data[position];
            String[] monthday = date.split("-");
            String total = "0";

            if (monthday.length == 3) {
                final int intYear = Integer.parseInt(monthday[0]);
                final int intMonth = Integer.parseInt(monthday[1]);
                final int intDay = Integer.parseInt(monthday[2]);

                Cursor cursor = dbOperation
                        .query(DBOperation.TABLE_NAME, null,
                                DBOperation.YEAR + " like ? AND "
                                        + DBOperation.MONTH + " like ? AND "
                                        + DBOperation.DAY + " like ? ",
                                new String[]{intYear + "",
                                        (intMonth + 1) + "", intDay + ""});
                boolean hasData = cursor.moveToFirst();
                if (hasData) {
                    int totalIndex = cursor.getColumnIndex(DBOperation.TOTAL);
                    total = cursor.getString(totalIndex);
                }

                if (intYear < year || (intYear == year && intMonth < month)) {
                    tv.setText(String.valueOf(intDay));
                    tv.setTextColor(0xff434343);
                    price.setText(" ");
                    tv.setClickable(false);
                } else if (intYear > year
                        || (intYear == year && intMonth > month)) {
                    tv.setText(String.valueOf(intDay));
                    tv.setTextColor(0xff434343);
                    tv.setClickable(false);
                } else {
                    tv.setTextColor(0xff000000);
                    if (intYear == currentyear && intMonth == currentmonth
                            && intDay == currentday) {
                        tv.setText("今天");
                    } else {
                        tv.setText(String.valueOf(intDay));
                    }
                    ll.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(),
                                    BudgetDetailActivity.class);
                            intent.putExtra("day", String.valueOf(intDay));
                            intent.putExtra("year", String.valueOf(intYear));
                            intent.putExtra("month",
                                    String.valueOf(intMonth + 1));
                            startActivityForResult(intent, 1);
                        }
                    });
                    total_month = total_month + Integer.valueOf(total);
                    tv_total_month.setText("本月共计花费：" + total_month + " ¥");
                    MyBudgetActivity.year = String.valueOf(intYear);
                    MyBudgetActivity.month = String.valueOf(intMonth + 1);
                }
                if (hasData) {
                    price.setText("¥ " + total);
                }

            }
            return ll;
        }

    }

    private void initTitle() {
        tv_head_title.setText("查看资产");
    }

    private void findViewById() {
        imv_left = (TextView) findViewById(R.id.left_img);
        imv_right = (TextView) findViewById(R.id.right_img);
        imv_left.setOnClickListener(this);
        imv_right.setOnClickListener(this);

        tv_total_month = (TextView) findViewById(R.id.tv_total_month);
        // tv_total_year = (TextView) findViewById(R.id.tv_total_year);
        gridView = (GridView) findViewById(R.id.gridview);
        tv_date = (TextView) findViewById(R.id.tv_month);
        tv_date.setOnClickListener(this);

        btn_left_header = (Button) this.findViewById(R.id.btn_left_header);
        btn_left_header.setOnClickListener(this);
        tv_head_title = (TextView) this.findViewById(R.id.tv_head_title);

    }

}
