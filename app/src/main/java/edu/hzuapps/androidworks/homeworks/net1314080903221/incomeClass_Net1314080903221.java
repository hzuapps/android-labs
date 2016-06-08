package models;

import android.content.Context;

public class incomeClass_Net1314080903221 extends TradeClass_Net1314080903221 {


	public incomeClass_Net1314080903221(int id, float money, String time, String mark,
										String packageType, Context context) {
		super(id, money, time, mark, packageType, context);
		// TODO Auto-generated constructor stub
	}
	public void trade_add(){
		super.settablename("tb_inaccount");
		super.trade_add();
	}
	public void trade_modify(){
		
	}
	public int trade_delect(int id){
		super.settablename("tb_inaccount");
		return super.trade_delect(id);
	}

}
