/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.data;

import java.util.Date;

/**
 * アーカイブの項目用のクラス
 *
 * @author uemura-a
 */
public class FussageArchive {

	private Date _date;

	private int _count;

	public int getCount() {
		return _count;
	}

	public void setCount(int count) {
		_count = count;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

}
