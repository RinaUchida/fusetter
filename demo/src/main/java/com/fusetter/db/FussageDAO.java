/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fusetter.data.Fussage;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;
import jp.co.shinko_1930.gypsophila.string.AES;

/**
 * 伏せ字ツイートを取得するクラス
 *
 * @author hata-k
 */
public class FussageDAO extends AbstractDAO {

	/**
	 * FussageDAOのインスタンスを生成します
	 */
	private FussageDAO() {
		super(TargetDataSource.Read);
	}

	/** 取得対象の伏せ字ID */
	private long _fussageId = -1;

	/**
	 * 伏せ字IDを設定します
	 *
	 * @param _fussageId	伏せ字ID
	 */
	private void setFussageId(long fussageId) {
		_fussageId = fussageId;
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

	/** 識別文字列 */
	private String _cryptogram;

	/**
	 * 識別文字列を設定します
	 *
	 * @param cryptogram	識別文字列
	 */
	private void setCryptogram(String cryptogram) {
		_cryptogram = cryptogram;
	}

	/** ステータスID一覧 */
	private long[] _statusIdList;

	/**
	 * ステータスID一覧を設定します
	 */
	private void setStatusIdList(long[] statusIdList) {
		_statusIdList = statusIdList;
	}

	/** 管理者かどうか */
	private boolean _admin = false;

	/**
	 * 管理者かどうかを設定します
	 *
	 * @param b	管理者かどうか
	 */
//	public void setAdmin(boolean b) {
//		_admin = b;
//	}

	/**
	 * 指定された伏せ字TDの伏せ字を取得する FussageDAO のインスタンスを生成します
	 *
	 * @param fussageId	伏せ字ID
	 * @return			FussageDAOのインスタンス
	 */
	public static FussageDAO createFussageByFussageId(long fussageId) {
		FussageDAO dao = new FussageDAO();
		dao.setFussageId(fussageId);
		return dao;
	}

	/**
	 * 指定された伏せ字TDの伏せ字を取得する FussageDAO のインスタンスを生成します
	 *
	 * @param fussageId	伏せ字ID
	 * @return			FussageDAOのインスタンス
	 */
	public static FussageDAO createFussageByFussageId(long fussageId, long userId) {
		FussageDAO dao = new FussageDAO();
		dao.setFussageId(fussageId);
		dao.setUserId(userId);
		return dao;
	}

	/**
	 * 指定されたユーザTDの伏せ字一覧を取得する FussageDAO のインスタンスを生成します
	 *
	 * @param userId	ユーザID
	 * @return			FussageDAOのインスタンス
	 */
	public static FussageDAO createFussageByUserId(long userId) {
		FussageDAO dao = new FussageDAO();
		dao.setUserId(userId);
		return dao;
	}

	/**
	 * 指定されたステータスTDの伏せ字を取得する FussageDAO のインスタンスを生成します
	 *
	 * @param statusIdList	ステータスID一覧
	 * @return			FussageDAOのインスタンス
	 */
	public static FussageDAO createFussageByStatusIdList(long[] statusIdList) {
		FussageDAO dao = new FussageDAO();
		dao.setStatusIdList(statusIdList);
		return dao;
	}
	/**
	 * 指定された識別文字列の伏せ字のみを取得する FussageDAO のインスタンスを生成します
	 *
	 * @param cryptogram	識別文字列
	 * @return				FussageDAOのインスタンス
	 */
	public static FussageDAO createFussageByCryptogram(String cryptogram) {
		FussageDAO dao = new FussageDAO();
		dao.setCryptogram(cryptogram);
		return dao;
	}

	/** 伏せ字ツイートを格納するbean */
	private Fussage[] _fussages;


	private long _maxId = -1;

	/**
	 * 取得するIDの最大値（このIDのツイートは含まない）を設定します
	 */
	public void setMaxId(long maxId) {
		_maxId = maxId;
	}

	private int _utfOffset = 0;

	public void setUtcOffset(int utcOffset) {
		_utfOffset = utcOffset;
	}

	private Date _periodTime;

	/**
	 * 取得対象の年月を設定します
	 */
	public void setPeriodTime(Date date) {
		_periodTime = date;
	}

	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {

		if (_statusIdList == null) {
			executeCommon(conn);
		}
		else {
			executeForStatusIdList(conn);
		}

	}

	private void executeCommon(Connection conn) throws Exception  {
		String tz = (_utfOffset / 60 / 60) + ":00";
		if (tz.startsWith("-") == false) {
			tz = "+" + tz;
		}


		String sql = "select " +
					 "	fussage.fsg_id," +
					 "	fsg_us_id," +
					 "	fsg_scr_name," +
					 "	fsg_tweet," +
					 "	fsg_turn_tweet," +
					 "	fsg_scope," +
					 "	fsg_us_list_id," +
					 "	fsg_us_list_name," +
					 "	fsg_cryptogram," +
					 "	convert_tz(fsg_datetime, '+9:00', '" + tz + "') as dt," +
					 "	fsg_status_id," +
		 			 "	fsg_del_flg," +
		 			 "	fsg_note," +
		 			 "	fsg_language," +
		 			 "  fsg_update, " +
		 			 "	us_img_url," +
		 			 "	fsg_views," +
		 			 /*"	us_use_views " +*/
		 			 //20190131 fsg_ad_levelの取得を追加
		 			 "	us_use_views, " +
		 			 "  fsg_ad_level " +
		 			 "    from fussage" +
		 			 "    left join fusetter_user on us_id=fsg_us_id " +
		 			 "    left join fussage_ad on fussage_ad.fsg_id=fussage.fsg_id " +
		 			 "    where";

					if (_admin) {
						sql += " fsg_us_id > 0 ";
					}
					else {
						 if(_fussageId != -1) {
							 sql += " fussage.fsg_id = ? ";
							 if(_userId != -1) {
								 sql += " and fsg_us_id=? ";
							 }
						 }
						 else if(_userId != -1) {
							 sql += " fsg_us_id = ?";
						 }
						 else {
			  				 sql += " fsg_cryptogram = ?";
			  			 }
					}

		 			 if (_maxId > -1) {
		 				 sql += " and fussage.fsg_id < ? ";
		 			 }

		 			 if (_periodTime != null) {
		 				 sql += " and convert_tz(fsg_datetime, '+9:00', '" + tz + "') >= ? ";
		 				 sql += " and convert_tz(fsg_datetime, '+9:00', '" + tz + "') < ? ";
		 			 }

		 			 sql += " and fsg_status_id is not null" +
		 			 		" and fsg_del_flg = 0" +
		 			 		" order by fsg_datetime desc, fsg_id desc limit 0, 21";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try{

			List<Object> params = new ArrayList<Object>();

			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (_admin == false) {
				if(_fussageId != -1){
					params.add(_fussageId);
					if(_userId != -1) {
						params.add(_userId);
					}
				}
				else if(_userId != -1) {
					params.add(_userId);
				}
				else {
					params.add(_cryptogram);
				}
			}

			if (_maxId > -1) {
				params.add(_maxId);
			}

			if (_periodTime != null) {

				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

				Calendar c = Calendar.getInstance();
				c.setTime(_periodTime);
				c.set(Calendar.DAY_OF_MONTH, 1);
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);

				params.add(df.format(c.getTime()));

				c.add(Calendar.MONTH, 1);

				params.add(df.format(c.getTime()));
			}

			setParameters(stmt, params.toArray());

			rs = stmt.executeQuery();

			List<Fussage> l = new ArrayList<Fussage>();

			while(rs.next()) {
				Fussage f = buildFussage(rs);
				l.add(f);
			}

			_fussages = l.toArray(new Fussage[0]);

		} finally {
			close(rs);
			close(stmt);
		}
	}


	private void executeForStatusIdList(Connection conn) throws Exception  {
		String tz = (_utfOffset / 60 / 60) + ":00";
		if (tz.startsWith("-") == false) {
			tz = "+" + tz;
		}

		//20190201　fussage_adテーブルを追加したため修正
		String sql = "select " +
					 "	fussage.fsg_id," +
					 "	fsg_us_id," +
					 "	fsg_scr_name," +
					 "	fsg_tweet," +
					 "	fsg_turn_tweet," +
					 "	fsg_scope," +
					 "	fsg_us_list_id," +
					 "	fsg_us_list_name," +
					 "	fsg_cryptogram," +
					 "	convert_tz(fsg_datetime, '+9:00', '" + tz + "') as dt," +
					 "	fsg_status_id," +
		 			 "	fsg_del_flg," +
		 			 "	fsg_note," +
		 			 "	fsg_language," +
		 			 "  fsg_update, " +
		 			 "	us_img_url," +
		 			 "	fsg_views," +
		 			 "	us_use_views, " +
		 			 //広告レベルを取得するように修正
		 			 "  fsg_ad_level"+
		 			 "    from fussage" +
		 			 "    left join fusetter_user on us_id=fsg_us_id " +
		 			 "    left join fussage_ad on fussage_ad.fsg_id=fussage.fsg_id"+
		 			 "    where fsg_status_id = ? and fsg_del_flg = 0";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try{


			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			List<Fussage> l = new ArrayList<Fussage>();

			for (long statusId : _statusIdList) {
				stmt.setLong(1, statusId);
				rs = stmt.executeQuery();
				if(rs.next()) {
					l.add(buildFussage(rs));
				}
			}

			_fussages = l.toArray(new Fussage[0]);

		} finally {
			close(rs);
			close(stmt);
		}
	}


	/**
	 * 結果セットからFussageを作成します
	 */
	private Fussage buildFussage(ResultSet rs) throws Exception {
		Fussage f = new Fussage();
		f.setId(rs.getLong("fsg_id"));
		f.setUserId(rs.getLong("fsg_us_id"));
		f.setScreenName(rs.getString("fsg_scr_name"));
		f.setOriginalTweet(AES.decrypt(rs.getString("fsg_tweet"), System.getProperty("sks.md5key")));
		f.setTurnTweet(rs.getString("fsg_turn_tweet"));
		f.setScope(rs.getInt("fsg_scope"));
		f.setListId(rs.getLong("fsg_us_list_id"));
		f.setListName(rs.getString("fsg_us_list_name"));
		f.setCryptogram(rs.getString("fsg_cryptogram"));
		f.setDatetime(rs.getTimestamp("dt"));
		f.setStatusId(rs.getLong("fsg_status_id"));
		f.setDeleteFlag(rs.getInt("fsg_del_flg"));
		{
			String s = rs.getString("fsg_note");
			if (s != null) {
				f.setNote(AES.decrypt(s, System.getProperty("sks.md5key")));
			}
			else {
				f.setNote(null);
			}
		}
		f.setLanguage(rs.getString("fsg_language"));
		f.setUpdate(rs.getInt("fsg_update") == 1);
		f.setImageUrl(rs.getString("us_img_url"));
		f.setViews(rs.getInt("fsg_views"));
		f.setUseViews(rs.getInt("us_use_views") == 1);
		//20190131 広告レベルを追加
		f.setAdLevel(rs.getInt("fsg_ad_level"));

		return f;
	}

	/**
	 * 伏せ字ツイートに関する情報を返します
	 *
	 * @return
	 */
	public Fussage[] getFussages() {
		return _fussages;
	}

}
