/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import jp.co.shinko_1930.gypsophila.db.AbstractDAO;

import com.fusetter.data.FusetterUser;

/**
 * ユーザー情報を更新するDAO
 *
 * @author hata-k
 *
 */
public class FusetterUserUpdateDAO extends AbstractDAO {

	public FusetterUserUpdateDAO() {
		super(TargetDataSource.Update);
	}

	private FusetterUser _user;

	public void setFusetterUser(FusetterUser user) {
		_user = user;
	}


	/* (非 Javadoc)
	 * @see jp.co.shinko_1930.gypsophila.db.AbstractDAO#executeImpl(java.sql.Connection)
	 */
	@Override
	public void executeImpl(Connection conn) throws Exception {

		PreparedStatement stmt = null;

		try{

			// 今のDBの情報を取得
			FusetterUser user = null;
			{
				FusetterUserDAO dao = new FusetterUserDAO();
				dao.setConnection(conn);
				dao.setUserId(_user.getUserId());
				dao.execute();
				user = dao.getFusetterUser();
			}


			// ユーザーがいなければ追加
			if (user == null) {

				stmt = conn.prepareStatement("insert into fusetter_user(us_id, us_scr_name, us_img_url,us_use_mypage,us_last_access,us_language, us_utc_offset) values (?,?,?,0,current_timestamp,?,?)");
				stmt.setLong(1, _user.getUserId());
				stmt.setString(2, _user.getScreenName());
				stmt.setString(3, _user.getImageUrl());
				stmt.setString(4, _user.getLanguage());
				stmt.setInt(5, _user.getUtcOffset());
				stmt.executeUpdate();
			}
			else {

				List<Object> params = new ArrayList<Object>();

				// 変更箇所を更新
				StringBuffer sb = new StringBuffer("update fusetter_user set ");

				if (user.getImageUrl().equals(_user.getImageUrl()) ==false) {
					sb.append(" us_img_url=?, ");
					params.add(_user.getImageUrl());
				}
				else if (user.getScreenName().equals(_user.getScreenName()) ==false) {
					sb.append(" us_scr_name=?, ");
					params.add(_user.getScreenName());
				}
				else if (user.getLanguage().equals(_user.getLanguage()) ==false) {
					sb.append(" us_language=?, ");
					params.add(_user.getLanguage());
				}
				else if (user.getUtcOffset() != _user.getUtcOffset()) {
					sb.append(" us_utc_offset=?, ");
					params.add(_user.getUtcOffset());
				}

				sb.append(" us_last_access=current_timestamp where us_id=?");
				params.add(_user.getUserId());

				stmt = conn.prepareStatement(sb.toString());
				setParameters(stmt, params.toArray());

				stmt.executeUpdate();

			}
		}
		finally {
			close(stmt);
		}

	}

}
