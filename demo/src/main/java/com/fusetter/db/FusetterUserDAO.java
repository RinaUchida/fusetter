/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;

import com.fusetter.data.FusetterUser;

/**
 * ユーザー情報を取得するDAO
 *
 * @author hata-k
 *
 */
public class FusetterUserDAO extends AbstractDAO {

	public FusetterUserDAO() {
		super(TargetDataSource.Read);
	}

	private long _userId = -1;

	public void setUserId(long userId) {
		_userId = userId;
	}

	private String _screenName;

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	private FusetterUser _user;

	public FusetterUser getFusetterUser() {
		return _user;
	}


	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try{

			if (_userId != -1) {
				stmt = conn.prepareStatement("select * from fusetter_user where us_id=?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setLong(1, _userId);
			}
			else {
				stmt = conn.prepareStatement("select * from fusetter_user where us_scr_name=? order by us_last_access desc", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setString(1, _screenName);
			}

			rs = stmt.executeQuery();

			if (rs.next()) {
				_user = new FusetterUser();
				_user.setUserId(rs.getLong("us_id"));
				_user.setScreenName(rs.getString("us_scr_name"));
				_user.setImageUrl(rs.getString("us_img_url"));
				_user.setUseMyPage(rs.getInt("us_use_mypage") == 1);
				_user.setLanguage(rs.getString("us_language"));
				_user.setUtcOffset(rs.getInt("us_utc_offset"));
				_user.setUseViews(rs.getInt("us_use_views") == 1);
			}

		}
		finally {
			close(rs);
			close(stmt);
		}

	}

}
