package com.example.sortlistview;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class Net1214080613131PinyinComparator implements Comparator<Net1214080613131SortModel> {

	public int compare(Net1214080613131SortModel o1, Net1214080613131SortModel o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
