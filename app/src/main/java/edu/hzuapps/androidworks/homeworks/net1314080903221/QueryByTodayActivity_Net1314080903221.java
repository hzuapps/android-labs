package com.example.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.MyPackage_Net1314080903221;
import models.TradeClass_Net1314080903221;
import models.consumeClass_Net1314080903221;
import models.incomeClass_Net1314080903221;

public class QueryByTodayActivity_Net1314080903221 extends Activity
{
  private ListView listView;
  private TextView textView;
  private Adapter_TD_Net1314080903221 myadapter;
  Map<TypeClass_Net1314080903221, Boolean> localmap;
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.querybytoday__net1314080903221);
    //ivinfo=(ListView)findViewById(R.id.listView);
    //setAdapter();
    GetTodayBill();
  }
  


  private void GetTodayBill() {
	// TODO Auto-generated method stub
	//ArrayAdapter<String>  adapter=null;
	listView =(ListView)findViewById(R.id.listView);
	textView =(TextView)findViewById(R.id.textView);
	List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
	float todaymenoy=0;
	MyPackage_Net1314080903221 pack=new MyPackage_Net1314080903221(this);
	List<TradeClass_Net1314080903221> List=pack.getAlltrade();
	//bill_array=new String[List.size()];
	int i=0;
    Calendar localCalendar = Calendar.getInstance();
    int year = localCalendar.get(Calendar.YEAR);
    int month = localCalendar.get(Calendar.MONTH)+1;
    int day = localCalendar.get(Calendar.DAY_OF_MONTH);
	String str1=new String(year+"-"+month+"-"+day);
	String str;
	for(TradeClass_Net1314080903221 con:List){
			str=con.gettime();
			if(str1.equals(str)){
				//bill_array[i]=con.getId()+"|*****|"+con.getMoney()+"\n"+con.getPocketType()+"|****|"+con.gettime();
				todaymenoy+=con.getMoney();
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("_id", con.getId());
				map.put("money", ""+con.getMoney());
				if(con.getPocketType().equals("日常购物")){
					map.put("icon",R.drawable.richanggouwu);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("交际送礼")){
					map.put("icon",R.drawable.jiaojisongli);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("餐饮开销")){
					map.put("icon",R.drawable.canyingkaixiao);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("购置衣物")){
					map.put("icon",R.drawable.gouziyiwu);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("娱乐开销")){
					map.put("icon",R.drawable.yulekaixiao);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("水电煤气")){
					map.put("icon",R.drawable.shuidianmeiqi);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("网费话费")){
					map.put("icon",R.drawable.wannluohuafei);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("交通出行")){
					map.put("icon",R.drawable.jiaotongchuxing);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("其他花费")){
					map.put("icon",R.drawable.qita);
					map.put("classtype",0);
				}else if(con.getPocketType().equals("工资收入")){
					map.put("icon",R.drawable.gongzi);
					map.put("classtype",1);
				}else if(con.getPocketType().equals("股票收入")){
					map.put("icon",R.drawable.gupiao);
					map.put("classtype",1);
				}else {
					map.put("icon",R.drawable.qita);
					map.put("classtype",1);
				}
				map.put("time", con.gettime());
				map.put("type", con.getPocketType());
				list.add(map);
				i++;
			}
	}
	localmap=new HashMap<TypeClass_Net1314080903221, Boolean>();
	myadapter=new Adapter_TD_Net1314080903221(this, list, localmap);
	textView.setText("今日共花费："+(-todaymenoy)+"元");
	listView.setAdapter(myadapter);
	if(i==0){
		Toast.makeText(getApplicationContext(), "今天您还没有消费哦！", Toast.LENGTH_SHORT);
	}
}

public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    paramMenu.add(0, 1, 1, "删除").setIcon(R.drawable.delete);
    return super.onCreateOptionsMenu(paramMenu);
  }


  public boolean onOptionsItemSelected(MenuItem paramMenuItem){
	  	// = Adapter_TD_Net1314080903221.isSelected;
	    if (localmap.size() <= 0)
	    {
	      Toast.makeText(this, "请先选择要删除的消费记录!", 0).show();
	      return true;
	    }
        consumeClass_Net1314080903221 tradeconsume=new consumeClass_Net1314080903221(0, 0, "", "123", "", QueryByTodayActivity_Net1314080903221.this);
        incomeClass_Net1314080903221 tradeincome=new incomeClass_Net1314080903221(0, 0, "", "123", "", QueryByTodayActivity_Net1314080903221.this);
	    Iterator it = localmap.entrySet().iterator();
	    while (it.hasNext()) {
		     Map.Entry entry = (Map.Entry) it.next();
		     Object key = entry.getKey();
		     Object value = entry.getValue();
		     if((Boolean)value){
		    	 if(((TypeClass_Net1314080903221)key).type==0){
		    		 int success=tradeconsume.trade_delect(((TypeClass_Net1314080903221)key)._id);
		    		 if(success==1)Toast.makeText(this, "删除消费记录成功!", 0).show();
		    	 }else{
		    		 int success=tradeincome.trade_delect(((TypeClass_Net1314080903221)key)._id);
		    		 if(success==1)Toast.makeText(this, "删除收入记录成功!", 0).show();
		    	 }
		    }
		     //Log.i("nihao","key=" + key + " value=" + value);
	    }
	    GetTodayBill(); 
	    return true;  
  }
  
  protected void onResume()
  {
	GetTodayBill();  
    super.onResume();
  }
}