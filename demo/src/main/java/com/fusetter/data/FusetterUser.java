/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.data;

/**
 * fusetterのユーザ情報
 *
 * @author uemura-a
 */
public class FusetterUser {

	private long	_userId;

	private String _screenName;

	private String _imageUrl;

	private boolean _useMyPage;

	private String _language;

	private int _utcOffset;

	private boolean _useViews;

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getScreenName() {
		return _screenName;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	public String getImageUrl() {
		return _imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		_imageUrl = imageUrl;
	}

	public boolean isUseMyPage() {
		return _useMyPage;
	}

	public void setUseMyPage(boolean useMyPage) {
		_useMyPage = useMyPage;
	}

	public String getLanguage() {
		return _language;
	}

	public void setLanguage(String language) {
		_language = language;
	}

	public int getUtcOffset() {
		return _utcOffset;
	}

	public void setUtcOffset(int utcOffset) {
		_utcOffset = utcOffset;
	}

	public void setUseViews(boolean b) {
		_useViews = b;
	}

	public boolean getUseViews() {
		return _useViews;
	}

}
