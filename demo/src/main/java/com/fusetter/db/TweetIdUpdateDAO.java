/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.fusetter.data.Fussage;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;

/**
 * ツイートIDを更新するDAO
 * 
 * @author hata-k
 *
 */
public class TweetIdUpdateDAO extends AbstractDAO {
	
	/**
	 * TweetIdUpdateDAOのインスタンスを生成します
	 */
	public TweetIdUpdateDAO() {
		super(TargetDataSource.Update);
	}
	
	/** 伏せ字ツイート情報 */
	private Fussage _fussage;
	
	/**
	 * 伏せ字ツイートに関する情報を設定します
	 * 
	 * @param fussage	伏せ字ツイートに関する情報
	 */
	public void setFussage(Fussage fussage) {
		_fussage = fussage;
	}
	

	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {
		
		String sql = "update fussage set fsg_datetime = ?, fsg_status_id = ? where fsg_cryptogram = ?";
		
		PreparedStatement stmt = null;
		
		try{
			
			stmt = conn.prepareStatement(sql);
			stmt.setTimestamp(1, new Timestamp(_fussage.getDatetime().getTime()));
			stmt.setLong(2, _fussage.getStatusId());
			stmt.setString(3, _fussage.getCryptogram());
			stmt.executeUpdate();
		
		}
		finally {
			close(stmt);
		}
		
	}

}
