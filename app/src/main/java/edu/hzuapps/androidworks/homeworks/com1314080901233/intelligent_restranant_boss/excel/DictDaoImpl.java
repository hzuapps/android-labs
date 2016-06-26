package com.example.intelligent_restranant_boss.excel;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class DictDaoImpl {

	public DictDaoImpl(Context context) {

	}

	public boolean isExist() {
		return false;
	}

	public List<Dict> getDictItem(String dictId) {
		String[] dicts = new String[5];
		List<Dict> retList = new ArrayList<Dict>();
		for (int j = 0; j < dicts.length; j++) {
			Dict dict = new Dict();
			dict.setName(dicts[j]);
			dict.setId(j + 1);
			retList.add(dict);
		}
		return retList;
	}

	public long insert(Dict entity) {
		return 1L;
	}

	public void delete(int id) {
	}

	public void update(Dict entity) {
	}

	public Dict get(Object id) {
		Dict dict = new Dict();
		return dict;
	}

	public void delete(Integer... ids) {
	}
}
