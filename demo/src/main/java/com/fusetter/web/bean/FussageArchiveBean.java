/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.web.bean;

/**
 * アーカイブの項目用クラス
 *
 * @author uemura-a
 */
public class FussageArchiveBean {

	private String _yyyymm;

	private String _label;

	private int _count;

	public String getYyyymm() {
		return _yyyymm;
	}

	public void setYyyymm(String yyyymm) {
		_yyyymm = yyyymm;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public int getCount() {
		return _count;
	}

	public void setCount(int count) {
		_count = count;
	}


}
