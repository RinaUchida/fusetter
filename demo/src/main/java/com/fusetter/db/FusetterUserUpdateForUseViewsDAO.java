/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;

/**
 * ユーザーが閲覧数を使うかどうかを取得するクラス
 *
 * @author uemura-a
 *
 */
public class FusetterUserUpdateForUseViewsDAO extends AbstractDAO {

	public FusetterUserUpdateForUseViewsDAO() {
		super(TargetDataSource.Update);
	}


	private long _userId;
	private int _useViewCount;



	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUseViewCount(int useViewCount) {
		_useViewCount = useViewCount;
	}

	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {

		PreparedStatement stmt = null;

		try{

			{

				String sql = "update fusetter_user set us_use_views=? where us_id=?";

				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, _useViewCount);
				stmt.setLong(2, _userId);

				stmt.executeUpdate();

			}
		}
		finally {
			close(stmt);
		}

	}

}
