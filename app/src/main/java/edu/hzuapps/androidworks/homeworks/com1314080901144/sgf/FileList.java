package com1314080901144.sgf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FileList extends ListActivity {

	private List<String> _items = null;
	private String filePath = "/";
	private String filter=null;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		filter=request();
		fillList(new File("/").listFiles());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (_items == null || _items.size() == 0)
			return;

		filePath = _items.get((int) id);
		File file = new File(filePath);
		if (file.isDirectory()) {
			try {
				fillList(file.listFiles());
			} catch (Exception ex) {
				Toast.makeText(FileList.this, ex.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		} else{
			response(filePath);
		}
	}

	private void fillList(File[] files) {
		List<String> items = new ArrayList<String>();
		for (File file : files){
			if(file.canRead()){
				if(file.isFile() && !file.getName().endsWith(filter))
					continue;
				items.add(file.getPath());
			}
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		setListAdapter(fileList);

		this._items = items;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			response("");
			return true;
		case KeyEvent.KEYCODE_DPAD_UP:
			fillList(new File("/").listFiles());
			filePath = "/";
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void response(String value) {
		Intent i = new Intent();
		Bundle b = new Bundle();
		b.putString("result", value);
		i.putExtras(b);

		this.setResult(RESULT_OK, i);
		this.finish();
	}
	
	private String request(){
		 Intent i = this.getIntent();
		 Bundle b = i.getExtras();
		 String s = b.getString("filter");
		 return s;
	}
}
