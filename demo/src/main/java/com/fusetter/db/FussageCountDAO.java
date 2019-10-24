/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;

/**
 * 伏せ字カウントを取得するDAOクラス
 *
 * @author hata-k
 */
public class FussageCountDAO extends AbstractDAO {

	/**
	 * FussageCountDAOのインスタンスを生成します
	 */
	public FussageCountDAO() {
		super(TargetDataSource.Read);
	}

	/** 伏せ字ツイート数 */
	private int _fussageCount;

	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try{

			stmt = conn.prepareStatement("select count(*) from fussage", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery();

			if(rs.next()) {
				_fussageCount = rs.getInt("count(*)");
			}

		}
		finally{
			close(rs);
			close(stmt);
		}

	}

	/**
	 * 伏せ字ツイート数を返します
	 *
	 * @return	伏せ字ツイート数
	 */
	public int getFussageCount() {
		return _fussageCount;
	}

}
