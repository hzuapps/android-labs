package com.example.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

class Adapter_TD_Net1314080903221 extends BaseAdapter
{
  public Map<TypeClass_Net1314080903221, Boolean> isSelected;
  public List<TypeClass_Net1314080903221> positions = null;
  private List<Map<String, Object>> data;
  private LayoutInflater mInflater;
  private Context paramContext0;
  public Adapter_TD_Net1314080903221(Context paramContext, List<Map<String, Object>> paramList, Map<TypeClass_Net1314080903221, Boolean> localmap)
  {
	paramContext0=paramContext;
    this.mInflater = LayoutInflater.from(paramContext);
    this.data = paramList;
    isSelected = localmap;
    //isSelected=new Map<Integer, Boolean>();
    positions = new ArrayList();
  }

  public int getCount()
  {
	  //return 1;
    if (this.data == null)
      return 0;
    return this.data.size();
  }

  public Object getItem(int paramInt)
  {
	  return paramInt;
    //return this.data.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  @Override
  public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup)
  {
	
	paramView=mInflater.inflate(R.layout.pocket_item__net1314080903221, null);
	ViewHolder localViewHolder=new ViewHolder();
	localViewHolder.img = ((ImageView)paramView.findViewById(R.id.imgTitle));
	localViewHolder.title = ((TextView)paramView.findViewById(R.id.typeName));
	localViewHolder.price = ((TextView)paramView.findViewById(R.id.moneyItem));
	localViewHolder.cBox = ((CheckBox)paramView.findViewById(R.id.check));
    (localViewHolder).img.setBackgroundResource(((Integer)((Map)this.data.get(paramInt)).get("icon")).intValue());
    localViewHolder.title.setText(((Map)this.data.get(paramInt)).get("type").toString());
    localViewHolder.price.setText("￥" + ((Map)this.data.get(paramInt)).get("money").toString());
    int a=((Integer) ((Map)this.data.get(paramInt)).get("_id")).intValue();
    int b=((Integer) ((Map)this.data.get(paramInt)).get("classtype")).intValue();
    TypeClass_Net1314080903221 tc=new TypeClass_Net1314080903221(a,b);
    positions.add(tc);
    //Toast.makeText(paramContext0, "ddd"+i, 0).show();
    localViewHolder.cBox.setOnCheckedChangeListener(
  		  new CompoundButton.OnCheckedChangeListener(){
	      public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
	      {
	    	  if(paramBoolean){
	    		  //Toast.makeText(paramContext0, "test"+paramInt, 0).show();
	    		  isSelected.put(positions.get(paramInt), true);
	    	  }else{
	    		  isSelected.put(positions.get(paramInt), false);
	    	  }
    	  }
      });
	return paramView;
	  
    }
  public final class ViewHolder
  {
    public CheckBox cBox;
    public ImageView img;
    public TextView price;
    public TextView title;

    public ViewHolder()
    {
    }
  }
  }

