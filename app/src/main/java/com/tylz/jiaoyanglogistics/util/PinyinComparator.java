package com.tylz.jiaoyanglogistics.util;

import com.tylz.jiaoyanglogistics.model.ContactsInfo;

import java.util.Comparator;

public class PinyinComparator implements Comparator<ContactsInfo> {

	@Override
	public int compare(ContactsInfo lhs, ContactsInfo rhs) {
		// TODO Auto-generated method stub
		return sort(lhs, rhs);
	}

	private int sort(ContactsInfo lhs, ContactsInfo rhs) {
		// 获取ascii值
		int lhs_ascii = lhs.sortLetter.toUpperCase().charAt(0);
		int rhs_ascii = rhs.sortLetter.toUpperCase().charAt(0);
		// 判断若不是字母，则排在字母之后
		if (lhs_ascii < 65 || lhs_ascii > 90)
			return 1;
		else if (rhs_ascii < 65 || rhs_ascii > 90)
			return -1;
		else
			return lhs.sortLetter.compareTo(rhs.sortLetter);
	}

}
