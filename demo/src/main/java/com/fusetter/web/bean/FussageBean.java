/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.web.bean;

import java.io.Serializable;


/**
 * 画面に表示する伏せ字ツイートに関する情報を格納するBean
 *
 * @author hata-k
 *
 */
public class FussageBean implements Serializable {

	private static final long serialVersionUID = -2322558931058997099L;

	/** ID */
	private long _id;

	/** スクリーンネーム */
	private String _screenName;

	/** 内容 */
	private String _Tweet;

	/** 伏せ字内容（Twitterに投稿される） */
	private String _turnTweet;

	/** 公開範囲 */
	private int _scope;

	/** 公開範囲の文字列表記 */
	private String _scopeName;

	/** TwitterCard用の公開範囲の文字列表記 */
	private String _scopeNameForTwCard;

	/** リストIDs */
	private long _listId;

	/** リスト名 */
	private String _listName;

	/** 識別文字列 */
	private String _cryptogram;

	/** 登録日時 */
	private String _datetime;

	/** ステータスID（ツイートのID） */
	private long _statusId;

	/** ノート */
	private String _note;

	/** 言語 */
	private String _language;

	/** 画像のURL */
	private String _imageUrl;

	/** オリジナルのツイート */
	private String _originalTweet;

	/** 更新されたかどうか */
	private boolean _updated;

	/** アクセス数 */
	private int _views;

	/** 閲覧数を使用するかどうか */
	private boolean _useViews;

	/** キャンペーンID */
	private String _fuseChar = "";

	/** 広告レベル */
	private int _adLevel;

	/** 20190218 TwitterCard画像 */
	private String _twitterImage;

	/**
	 * IDを返します
	 *
	 * @return ID
	 */
	public long getId() {
		return _id;
	}

	/**
	 * IDを設定します
	 *
	 * @param id ID
	 */
	public void setId(long id) {
		_id = id;
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
	 * 内容を返します
	 *
	 * @return 内容
	 */
	public String getTweet() {
		return _Tweet;
	}

	/**
	 * 内容を設定します
	 *
	 * @param tweet 内容
	 */
	public void setTweet(String tweet) {
		_Tweet = tweet;
	}

	/**
	 * 伏せ字ツイートを返します
	 *
	 * @return 伏せ字ツイート
	 */
	public String getTurnTweet() {
		return _turnTweet;
	}

	/**
	 * 伏せ字ツイートを設定します
	 *
	 * @param turnTweet 伏せ字ツイート
	 */
	public void setTurnTweet(String turnTweet) {
		_turnTweet = turnTweet;
	}

	/**
	 * 公開範囲を返します
	 *
	 * @return scope	公開範囲
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
	public String getDatetime() {
		return _datetime;
	}

	/**
	 * 登録日時を設定します
	 *
	 * @param datetime 登録日時
	 */
	public void setDatetime(String datetime) {
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
	 * 公開範囲の文字列表記を返します
	 *
	 * @return 公開範囲の文字列表記
	 */
	public String getScopeName() {
		return _scopeName;
	}

	/**
	 * 公開範囲の文字列表記の文字列表記を設定します
	 *
	 * @param scopeName 公開範囲の文字列表記
	 */
	public void setScopeName(String scopeName) {
		_scopeName = scopeName;
	}

	public String getScopeNameForTwCard() {
		return _scopeNameForTwCard;
	}

	public void setScopeNameForTwCard(String scopeNameForTwCard) {
		_scopeNameForTwCard = scopeNameForTwCard;
	}

	public String getNote() {
		return _note;
	}

	public void setNote(String note) {
		_note = note;
	}

	public String getLanguage() {
		return _language;
	}

	public void setLanguage(String language) {
		_language = language;
	}

	public boolean withNote() {
		return _note != null && _note.length() > 0;
	}

	private String _originalTweetWithSpan;

	public void setOriginalTweetWithSpan(String s) {
		_originalTweetWithSpan = s;
	}

	public String getOriginalTweetWithSpan() {
		return _originalTweetWithSpan;
	}

	public String getImageUrl() {
		return _imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		_imageUrl = imageUrl;
	}

	public String getOriginalTweet() {
		return _originalTweet;
	}

	public void setOriginalTweet(String originalTweet) {
		_originalTweet = originalTweet;
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

	public String getFuseChar() {
		return _fuseChar;
	}

	public void setFuseChar(String fuseChar) {
		_fuseChar = fuseChar;
	}

	public int getAdLevel() {
		return _adLevel;
	}

	public void setAdLevel(int adLevel) {
		_adLevel = adLevel;
	}

	public String getTwitterImage() {
		return _twitterImage;
	}

	public void setTwitterImage(String twitterImage) {
		_twitterImage = twitterImage;
	}


}