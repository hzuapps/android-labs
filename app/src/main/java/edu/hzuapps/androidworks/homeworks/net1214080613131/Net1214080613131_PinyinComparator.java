package edu.hzuapps.androidworks.homeworks;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class Net1214080613131_PinyinComparator implements Comparator<Net1214080613131_SortModel> {

	public int compare(Net1214080613131_SortModel o1, Net1214080613131_SortModel o2) {
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
