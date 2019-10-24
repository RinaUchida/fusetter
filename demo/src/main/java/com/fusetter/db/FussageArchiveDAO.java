/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;

import com.fusetter.data.FussageArchive;

/**
 * 伏せ字ツイートの月別の件数を取得する
 *
 * @author uemura-a
 */
public class FussageArchiveDAO extends AbstractDAO {

	/**
	 * FussageDAOのインスタンスを生成します
	 */
	private FussageArchiveDAO() {
		super(TargetDataSource.Read);
	}

	/** 取得対象のユーザID */
	private long _userId = -1;

	/**
	 * ユーザIDを設定します
	 *
	 * @param userId	ユーザID
	 */
	private void setUserId(long userId) {
		_userId = userId;
	}

	public static FussageArchiveDAO createFussageByUserId(long userId) {
		FussageArchiveDAO dao = new FussageArchiveDAO();
		dao.setUserId(userId);
		return dao;
	}


	private int _utfOffset = 0;

	public void setUtcOffset(int utcOffset) {
		_utfOffset = utcOffset;
	}



	private FussageArchive[] _fussageArchives;


	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {

		String tz = (_utfOffset / 60 / 60) + ":00";
		if (tz.startsWith("-") == false) {
			tz = "+" + tz;
		}

		String sql = " select date_format(convert_tz(fsg_datetime, '+9:00', '" + tz + "'), '%Y%m'), min(convert_tz(fsg_datetime, '+9:00', '" + tz + "')), count(*) from fussage where fsg_us_id=? and fsg_status_id is not null and fsg_del_flg = 0 group by 1 order by 1 desc";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try{

			List<Object> params = new ArrayList<Object>();

			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			params.add(_userId);

			setParameters(stmt, params.toArray());

			rs = stmt.executeQuery();

			List<FussageArchive> l = new ArrayList<FussageArchive>();

			while(rs.next()) {
				FussageArchive f = new FussageArchive();

				f.setDate(rs.getTimestamp(2));
				f.setCount(rs.getInt(3));

				l.add(f);
			}

			_fussageArchives = l.toArray(new FussageArchive[0]);

		} finally {
			close(rs);
			close(stmt);
		}

	}

	public FussageArchive[] getFussageArchives() {
		return _fussageArchives;
	}

}
