/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;
import jp.co.shinko_1930.gypsophila.string.AES;

/**
 * 伏せ字ツイートを更新するDAO
 *
 * @author uemura-a
 *
 */
public class FussageUpdateDAO extends AbstractDAO {

	/**
	 * FussageDeleteDAOのインスタンスを生成します
	 */
	public FussageUpdateDAO() {
		super(TargetDataSource.Update);
	}

	/** ユーザID */
	private long _userId;

	private int _scope = -1;

	private long _fussageId;

	public String _turnTweet;

	public String _originalTweet;

	private long _listId;

	private String _listName;

	public String _note;

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setScope(int scope) {
		_scope = scope;
	}

	public void setFussageId(long fussageId) {
		_fussageId = fussageId;
	}

	public void setTurnTweet(String turnTweet) {
		_turnTweet = turnTweet;
	}

	public void setOriginalTweet(String originalTweet) {
		_originalTweet = originalTweet;
	}

	public void setListId(long listId) {
		_listId = listId;
	}

	public void setListName(String listName) {
		_listName = listName;
	}

	public void setNote(String note) {
		_note = note;
	}

	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {


		List<Object> params = new ArrayList<Object>();

		String sql = "update fussage set fsg_update=1 ";

		{
			// 公開範囲
			if (_scope > -1) {
				sql += " ,fsg_scope=? ";

				params.add(_scope);

				// リストの場合
				if (_scope == 4) {
					sql += " ,fsg_us_list_id=? ,fsg_us_list_name=? ";
					params.add(_listId);
					params.add(_listName);
				}
			}

			// ツイート
			if (_originalTweet != null) {
				sql += " ,fsg_tweet=?, fsg_turn_tweet=? ";

				params.add(AES.encrypt(_originalTweet, System.getProperty("sks.md5key")));
				params.add(_turnTweet);
			}

			// ノート
			if (_note != null) {
				sql += " ,fsg_note = ? ";
				params.add(AES.encrypt(_note, System.getProperty("sks.md5key")));
			}

			sql += " where fsg_us_id = ? and fsg_id = ?";
			params.add(_userId);
			params.add(_fussageId);
		}

		PreparedStatement stmt = null;

		try{

			stmt = conn.prepareStatement(sql);
			setParameters(stmt, params.toArray());
			stmt.executeUpdate();

		}finally {
			close(stmt);
		}

	}

}
