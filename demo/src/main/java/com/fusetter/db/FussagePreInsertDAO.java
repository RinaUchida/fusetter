/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;
import jp.co.shinko_1930.gypsophila.string.AES;

import com.fusetter.data.Fussage;

/**
 * 伏せ字ツイートを仮登録するDAOクラス
 * （Twitterに登録する前に識別文字列を確定させる必要があるため）
 *
 * @author hata-k
 *
 */
public class FussagePreInsertDAO extends AbstractDAO {

	/**
	 * FussagePreInsertDAOのインスタンスを生成します
	 */
	public FussagePreInsertDAO() {
		super(TargetDataSource.Update);
	}

	/** 登録する伏せ字ツイート情報 */
	private Fussage _fussage;

	/**
	 * 伏せ字ツイート情報を設定します
	 *
	 * @param fussage	伏せ字ツイート情報
	 */
	public void setFussage(Fussage fussage) {
		_fussage = fussage;
	}

	/** 識別文字列 */
	private String _cryptogram;

	/**
	 * 識別文字列を設定します
	 *
	 * @param cryptogram	識別文字列
	 */
	public void setCryptogram(String cryptogram) {
		_cryptogram = cryptogram;
	}

	/**  伏せ字ツイートの登録に成功したかどうか */
	private boolean _successFlag = false;


	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {

		PreparedStatement stmt = null;

		try{
			String sql = "insert into " +
						 "  fussage(fsg_id, fsg_us_id, fsg_scr_name, fsg_tweet, fsg_turn_tweet, fsg_scope, fsg_us_list_id, fsg_us_list_name, fsg_cryptogram, fsg_datetime, fsg_status_id, fsg_del_flg, fsg_note, fsg_language) " +
						 "  values(default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?) ";

			stmt = conn.prepareStatement(sql);
			// ユーザid
			stmt.setLong(1, _fussage.getUserId());
			// スクリーンネーム
			stmt.setString(2, _fussage.getScreenName());
			// 内容
			stmt.setString(3, AES.encrypt(_fussage.getOriginalTweet(), System.getProperty("sks.md5key")));
			// 伏せ字内容（Twitterに投稿される）
			stmt.setString(4, _fussage.getTurnTweet());
			// 公開範囲
			stmt.setInt(5, _fussage.getScope());
			// リスト
			if(_fussage.getScope() == 4) {
				stmt.setLong(6, _fussage.getListId());
				stmt.setString(7, _fussage.getListName());
			}else {
				stmt.setNull(6, Types.INTEGER);
				stmt.setNull(7, Types.VARCHAR);
			}
			// 識別文字列
			stmt.setString(8, _cryptogram);
			// 登録日時
			stmt.setNull(9, Types.DATE);
			// ステータスid（ツイートのid）
			stmt.setNull(10, Types.BIGINT);

			// ノート
			if (_fussage.withNote()) {
				stmt.setString(11, AES.encrypt(_fussage.getNote(), System.getProperty("sks.md5key")));
			}
			else {
				stmt.setNull(11, Types.VARCHAR);
			}
			// 言語
			stmt.setString(12, _fussage.getLanguage());

			stmt.executeUpdate();

			_successFlag = true;

		}finally {
			close(stmt);
		}

	}

	/**
	 * 伏せ字ツイートの登録に成功したかどうかを返します
	 *
	 * @return	成功した場合はtrue、失敗した場合はfalse
	 */
	public boolean isSuccess() {
		return _successFlag;
	}

}
