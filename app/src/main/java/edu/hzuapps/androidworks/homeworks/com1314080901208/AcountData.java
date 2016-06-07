package com.example.passwordbox;

public class AcountData {
	private int acountId;
	private String acountLabel;
	public AcountData(int id,String user_name)
	{
		this.acountId=id;
		this.acountLabel=user_name;
	}
	public int getAcountId()
	{
		return acountId;
	}
	public String getAcountLabel()
	{
		return acountLabel;
	}

}
