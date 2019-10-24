/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.web.bean;

import java.io.Serializable;

/**
 * Twitterのユーザーリストの情報を格納するbean
 *
 * @author hata-k
 *
 */
public class ListBean implements Serializable {

	private static final long serialVersionUID = -8250549312557813559L;

	/** リスト名 */
	private String _name;

	/** リストID */
	private long _id;

	/**
	 * リスト名を返します
	 *
	 * @return リスト名
	 */
	public String getName() {
		return _name;
	}

	/**
	 * リスト名を設定します
	 *
	 * @param name リスト名
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * リストIDを設定します
	 *
	 * @return リストID
	 */
	public long getId() {
		return _id;
	}

	/**
	 * リストIDを設定します
	 *
	 * @param listId リストID
	 */
	public void setId(long id) {
		_id = id;
	}

}
