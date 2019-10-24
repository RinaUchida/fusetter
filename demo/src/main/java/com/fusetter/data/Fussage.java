/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.data;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import jp.co.shinko_1930.gypsophila.string.Sanitize;

/**
 * 伏せ字ツイートに関する情報を格納するクラス
 *
 * @author hata-k
 *
 */
public class Fussage {

	/** id */
	private long _id;

	/** ユーザid */
	private long _userId;

	/** スクリーンネーム */
	private String _screenName;

	/** 内容 */
	private String _originalTweet;

	/** 伏せ字内容（Twitterに投稿される） */
	private String _turnTweet;

	/** 公開範囲 */
	private int _scope;

	/** リストid */
	private long _listId;

	/** リスト名 */
	private String _listName;

	/** 識別文字列 */
	private String _cryptogram;

	/** 登録日時 */
	private Date _datetime;

	/** ステータスid（ツイートのid） */
	private long _statusId;

	/** 削除フラグ */
	private int _deleteFlag;

	/** ノート */
	private String _note;

	/** 言語 */
	private String _language;

	private String _imageUrl;

	private boolean _updated;

	private boolean _useViews;

	/** アクセス数 */
	private int _views;

	/** 20190131 広告レベル */
	private int _adLevel;

	/** 20190218 TwitterCard画像 */
	private String _twitterImage;

	/**
	 * ユーザIDを返します
	 *
	 * @return ユーザID
	 */
	public long getId() {
		return _id;
	}

	/**
	 * ユーザIDを設定します
	 *
	 * @param id ユーザID
	 */
	public void setId(long id) {
		_id = id;
	}

	/**
	 * ユーザIDを返します
	 *
	 * @return ユーザID
	 */
	public long getUserId() {
		return _userId;
	}

	/**
	 * ユーザIDを設定します
	 *
	 * @param userId ユーザID
	 */
	public void setUserId(long userId) {
		_userId = userId;
	}

	/**
	 * スクリーンネームを返します
	 *
	 * @return スクリーンネーム
	 */
	public String getScreenName() {
		return _screenName;
	}

	/**
	 * スクリーンネームを設定します
	 *
	 * @param screenName スクリーンネーム
	 */
	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	/**
	 * オリジナルのツイートを返します
	 *
	 * @return オリジナルのツイート
	 */
	public String getOriginalTweet() {
		return _originalTweet;
	}

	/**
	 * オリジナルのツイートを設定します
	 *
	 * @param tweet オリジナルのツイート
	 */
	public void setOriginalTweet(String originalTweet) {
		_originalTweet = originalTweet;
	}

	/**
	 * 伏せられたツイートを返します
	 *
	 * @return 伏せられたツイート
	 */
	public String getTurnTweet() {
		return _turnTweet;
	}

	/**
	 * 伏せられたツイートを設定します
	 *
	 * @param turnTweet 伏せられたツイート
	 */
	public void setTurnTweet(String turnTweet) {
		_turnTweet = turnTweet;
	}

	/**
	 * 公開範囲を返します
	 *
	 * @return 公開範囲
	 */
	public int getScope() {
		return _scope;
	}

	/**
	 * 公開範囲を設定します
	 *
	 * @param scope 公開範囲
	 */
	public void setScope(int scope) {
		_scope = scope;
	}

	/**
	 * リストIDを返します
	 *
	 * @return リストID
	 */
	public long getListId() {
		return _listId;
	}

	/**
	 * リストIDを設定します
	 *
	 * @param listId リストID
	 */
	public void setListId(long listId) {
		_listId = listId;
	}

	/**
	 * リスト名を返します
	 *
	 * @return リスト名
	 */
	public String getListName() {
		return _listName;
	}

	/**
	 * リスト名を設定します
	 *
	 * @param listName リスト名
	 */
	public void setListName(String listName) {
		_listName = listName;
	}

	/**
	 * 識別文字列を返します
	 *
	 * @return 識別文字列
	 */
	public String getCryptogram() {
		return _cryptogram;
	}

	/**
	 * 識別文字列を設定します
	 *
	 * @param cryptogram 識別文字列
	 */
	public void setCryptogram(String cryptogram) {
		_cryptogram = cryptogram;
	}

	/**
	 * 登録日時を返します
	 *
	 * @return 登録日時
	 */
	public Date getDatetime() {
		return _datetime;
	}

	/**
	 * 登録日時を設定します
	 *
	 * @param datetime 登録日時
	 */
	public void setDatetime(Date datetime) {
		_datetime = datetime;
	}

	/**
	 * ステータスIDを返します
	 *
	 * @return ステータスID
	 */
	public long getStatusId() {
		return _statusId;
	}

	/**
	 * ステータスIDを設定します
	 *
	 * @param statusId ステータスID
	 */
	public void setStatusId(long statusId) {
		_statusId = statusId;
	}

	/**
	 * 削除フラグを返します
	 *
	 * @return 削除フラグ
	 */
	public int isDeleteFlag() {
		return _deleteFlag;
	}

	/**
	 * 削除フラグを設定します
	 *
	 * @param deleteFlag 削除フラグ
	 */
	public void setDeleteFlag(int deleteFlag) {
		_deleteFlag = deleteFlag;
	}

	/**
	 * 公開するための余分な[]を取り除いたツイートを返します
	 *
	 * @return	公開するための余分な[]を取り除いたツイート
	 */
	public String getTweet() {

		String contents = _originalTweet;

		int start = 0;
		int endIndex = -1;
		while (true) {

			int e1 = contents.indexOf("]", start);
			int e2 = contents.indexOf("］", start);
			if (e1 == -1) {
				endIndex = e2;
			}
			else if (e2 == -1) {
				endIndex = e1;
			}
			else {
				endIndex = Math.min(e1, e2);
			}
			if (endIndex == -1) {
				break;
			}

			int startIndex = Math.max(contents.lastIndexOf("[", endIndex), contents.lastIndexOf("［", endIndex));
			if (startIndex == -1) {
				start = endIndex + 1;
				if (start == 0) {
					break;
				}
			}
			else {
				String s = contents.substring(0, startIndex);
				s += contents.substring(startIndex + 1, endIndex);
				s += contents.substring(endIndex + 1, contents.length());

				contents = s;
			}
		}

		return contents;

	}



	/**
	 * 公開するための余分な[]を取り除いたツイートを返します
	 *
	 * @return	公開するための余分な[]を取り除いたツイート
	 */
	public String getOriginalTweetWithSpanTag() {

		String contents = _originalTweet;

		// 開始タグと閉じタグに後から置き換えるランダムな文字列をつくる
		String spanTag1, spanTag2;
		while (true) {

			spanTag1 = RandomStringUtils.randomAlphanumeric(10);
			spanTag2 = RandomStringUtils.randomAlphanumeric(10);

			if (spanTag1.equals(spanTag2) == false && contents.indexOf(spanTag1) == -1 && contents.indexOf(spanTag2) == -1) {
				break;
			}
		}


		int start = 0;
		int endIndex = -1;
		while (true) {

			int e1 = contents.indexOf("]", start);
			int e2 = contents.indexOf("］", start);
			if (e1 == -1) {
				endIndex = e2;
			}
			else if (e2 == -1) {
				endIndex = e1;
			}
			else {
				endIndex = Math.min(e1, e2);
			}
			if (endIndex == -1) {
				break;
			}

			int startIndex = Math.max(contents.lastIndexOf("[", endIndex), contents.lastIndexOf("［", endIndex));
			if (startIndex == -1) {
				start = endIndex + 1;
				if (start == 0) {
					break;
				}
			}
			else {
				String s = contents.substring(0, startIndex);
				s += spanTag1;
				s += contents.substring(startIndex + 1, endIndex);
				s += spanTag2;
				s += contents.substring(endIndex + 1, contents.length());

				contents = s;
			}
		}

		// サニタイズ処理
		contents = Sanitize.sanitize(contents, true);

		// spanタグに置き換え
		contents = StringUtils.replace(contents, spanTag1, "<span>");
		contents = StringUtils.replace(contents, spanTag2, "</span>");

		return contents;

	}


	public String getNote() {
		return _note;
	}

	public void setNote(String note) {
		_note = note;
	}

	public boolean withNote() {
		return _note != null && _note.length() > 0;
	}

	public String getLanguage() {
		return _language;
	}

	public void setLanguage(String language) {
		_language = language;
	}

	public String getImageUrl() {
		return _imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		_imageUrl = imageUrl;
	}

	public void setUpdate(boolean b) {
		_updated = b;
	}

	public boolean isUpdated() {
		return _updated;
	}

	public int getViews() {
		return _views;
	}

	public void setViews(int views) {
		_views = views;
	}

	public void setUseViews(boolean b) {
		_useViews = b;
	}

	public boolean getUseViews() {
		return _useViews;
	}

	/**
	 * 広告レベルを設定します
	 *
	 * @param ad_level 広告レベル
	 */
	public int getAdLevel() {
		return _adLevel;
	}

	public void setAdLevel(int adLevel) {
		_adLevel = adLevel;
	}

	/**
	 * twitterCard画像
	 *
	 * @param twitter_image
	 */
	public String getTwitterImage() {
		return _twitterImage;
	}

	public void setTwitterImage(String twitterImage) {
		_twitterImage = twitterImage;
	}

}
