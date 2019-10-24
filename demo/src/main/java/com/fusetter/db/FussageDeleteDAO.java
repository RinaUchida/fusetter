/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;

/**
 * 伏せ字ツイートを削除するDAO
 * 
 * @author hata-k
 *
 */
public class FussageDeleteDAO extends AbstractDAO {
	
	/**
	 * FussageDeleteDAOのインスタンスを生成します
	 */
	public FussageDeleteDAO() {
		super(TargetDataSource.Update);
	}
	
	/** ユーザID */
	private long _userId;
	
	/**
	 * ユーザidを設定します
	 * 
	 * @param userId
	 */
	public void setUserId(long userId) {
		_userId = userId;
	}
	
	/** ステータスID */
	private long _statusId;
	
	/**
	 * ステータスid（ツイートのid）を設定します
	 * 
	 * @param statusId
	 */
	public void setStatusId(long statusId) {
		_statusId = statusId;
	}
	
	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {

		String sql = "update fussage set fsg_del_flg = 1 where fsg_us_id = ? and fsg_status_id = ?";
		PreparedStatement stmt = null;
		
		try{

			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, _userId);
			stmt.setLong(2, _statusId);
			stmt.executeUpdate();
		
		}finally {
			close(stmt);
		}
		
	}

}
