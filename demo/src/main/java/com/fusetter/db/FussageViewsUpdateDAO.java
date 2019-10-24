/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;

/**
 * 表示数を更新するDAO
 *
 * @author uemura-a
 *
 */
public class FussageViewsUpdateDAO extends AbstractDAO {

	/**
	 * FussageDeleteDAOのインスタンスを生成します
	 */
	public FussageViewsUpdateDAO() {
		super(TargetDataSource.Update);
	}

	/** アクセスしたユーザID */
	private long _accessUserId;

	/** 伏せ字ID */
	private long _fussageId;

	public void setAccessUserId(long accessUserId) {
		_accessUserId = accessUserId;
	}


	public void setFussageId(long fussageId) {
		_fussageId = fussageId;
	}

	private int _newViews;

	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {


		String sql = "update fussage set fsg_views=fsg_views + 1, fsg_last_view_us_id=? where fsg_id=? and fsg_us_id<>? and (fsg_last_view_us_id is null or fsg_last_view_us_id <> ?)";

		PreparedStatement stmt = null;
		try{

			stmt = conn.prepareStatement(sql);

			stmt.setLong(1, _accessUserId);
			stmt.setLong(2, _fussageId);
			stmt.setLong(3, _accessUserId);
			stmt.setLong(4, _accessUserId);

			stmt.executeUpdate();

		}finally {
			close(stmt);
		}

		FussageDAO dao = FussageDAO.createFussageByFussageId(_fussageId);
		dao.setConnection(conn);
		dao.execute();
		_newViews = dao.getFussages()[0].getViews();

	}

	public int getNewViews() {
		return _newViews;
	}

}
