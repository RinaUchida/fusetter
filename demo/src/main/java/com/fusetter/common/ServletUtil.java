/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fusetter.data.FusetterUser;
import com.fusetter.data.Fussage;
import com.fusetter.db.FusetterUserUpdateDAO;
import com.fusetter.db.FussageViewsUpdateDAO;
import com.fusetter.web.bean.FussageBean;

import jp.co.shinko_1930.gypsophila.web.resource.ResourceManager;
import twitter4j.Paging;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;

/**
 * @author hata-k
 *
 */
public class ServletUtil {

	//TODO uchida memo ほぼ変えていないが、パッケージをcommonに変更した

	public static void updateUser(HttpServletRequest request, User user, String language) throws Exception {


		// セッション設定
		int utcOffset = 0;
		{
			// プロテクトユーザかどうかを設定する
			request.getSession().setAttribute("protectedUser", user.isProtected() ?		 "1" : "0");
			// 画像のURLを設定する （20181206 httpsに変更）
			request.getSession().setAttribute("profileImageURL", user.getProfileImageURLHttps());


			// クッキーからオフセットを取得
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equalsIgnoreCase("tzoffset")) {
						try {
							utcOffset= - Integer.parseInt(cookie.getValue()) * 60;
						}
						catch(Exception e) {}
						break;
					}
				}
			}


			// UTCオフセットを設定
			request.getSession().setAttribute("utcOffset", String.valueOf(utcOffset));
		}

		// DB更新
		{
			FusetterUser fuser = new FusetterUser();
			fuser.setUserId(user.getId());
			//画像のURLを設定する（20181206 httpsに変更）
			fuser.setImageUrl(user.getProfileImageURLHttps());
			fuser.setLanguage(language);
			fuser.setScreenName(user.getScreenName());
			fuser.setUseMyPage(false);
			fuser.setUtcOffset(utcOffset);

			FusetterUserUpdateDAO dao = new FusetterUserUpdateDAO();
			dao.setFusetterUser(fuser);
			dao.execute();
		}

	}

	/**
	 * myselfやallなどのリクエストパラメタから、表示名を返します（リストはちょっと特殊）
	 */
	public static String getScopeString(HttpServletRequest request, String lang, String scopeParameter) {
		ResourceManager resourceManager = (ResourceManager)request.getServletContext().getAttribute("resourceManager");

		// 自分のみ
		if(scopeParameter.equals("myself")) {
			return resourceManager.getString(lang, "common.type.myself");
		}
		// だれでも
		else if(scopeParameter.equals("all")) {
			return resourceManager.getString(lang, "common.type.all");
		}
		// フォロワー
		else if(scopeParameter.equals("follower")) {
			return resourceManager.getString(lang, "common.type.follower");
		}
		// 相互フォロー
		else if(scopeParameter.equals("friend")) {
			return resourceManager.getString(lang, "common.type.friend");
		}
		// リツイートした人
		else if(scopeParameter.equals("retweet")) {
			return resourceManager.getString(lang, "common.type.retweet");
		}
		// @付きの人
		else if(scopeParameter.equals("mention")) {
			return resourceManager.getString(lang, "common.type.mention");
		}
		// リスト
		else {
			String[] s = scopeParameter.split(":");

			return resourceManager.getString(lang, "common.type.list") +
					 resourceManager.getString(lang, "common.type.list_prefix") +
					 s[1] +
					 resourceManager.getString(lang, "common.type.list_suffix");
		}

	}

	private static long _lastModified;
	private static List<String> _filters = new ArrayList<String>();

	/**
	 * ユーザーエージェントからボット等を判定します
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private static boolean checkFilter(HttpServletRequest request) throws Exception {

		File file = new File(request.getServletContext().getRealPath("/WEB-INF/view_filter"));
		synchronized (ServletUtil.class) {
			if (file.isFile() && file.lastModified() != _lastModified) {

				BufferedReader in = new BufferedReader(new FileReader(file));
				String line;
				_filters = new ArrayList<String>();
				while ((line = in.readLine()) != null) {
					line = line.trim();
					if (line.isEmpty() == false) {
						_filters.add(line.toLowerCase());
					}
				}
				in.close();

				_lastModified = file.lastModified();
			}
		}

		String ua = request.getHeader("user-agent");
		if (ua == null) {
			return false;
		}

		for (String filter : _filters) {
			if (ua.toLowerCase().indexOf(filter) > -1) {
				return true;
			}
		}

		return false;
	}

	public static void countUpViews(HttpServletRequest request, long fussageId) throws Exception {

		// 昔（2013/8よりも過去）のはカウントしない
		FussageBean bean = (FussageBean)request.getAttribute("fussageBean");
		if (bean.getViews() < 0) {
			return;
		}

		// ボットとかの場合はカウントしない
		if (checkFilter(request) == true) {
			return;
		}


		// ユーザIDを取得
		long userId = -1;
		{
			Twitter twitter = (Twitter)request.getSession().getAttribute("twitter");
			try {
				twitter.getScreenName();
				userId = twitter.getId();
			}
			catch(Exception e) {}

			if (userId == -1) {
				// IPアドレスを基にダミーのユーザIDを作成
				try {
					String[] s = request.getRemoteAddr().split("\\.");
					if (s.length == 4) {
						userId = 0;
						for (int i = 0; i < s.length; i ++) {
							userId = userId << 8;
							userId += Integer.parseInt(s[i]);
						}
					}
				}
				catch(Exception e) {}
			}
		}


		// DB内のアクセス数をアップする
		int newViews;
		{
			FussageViewsUpdateDAO dao = new FussageViewsUpdateDAO();
			dao.setFussageId(fussageId);
			dao.setAccessUserId(userId);
			dao.execute();

			newViews = dao.getNewViews();
		}

		// Bean内のカウントをアップする
		if (bean != null) {
			bean.setViews(newViews);
		}
	}


	/**
	 * 伏せ字ツイートにアクセスできるかどうか
	 *
	 * @param twitter	Twitter
	 * @param fussage	伏せ字ツイート
	 * @return
	 */
	public static boolean acceptTweet(Twitter twitter, Fussage fussage, HttpSession session) throws Exception {

		// 自分がした伏せ字ツイートはTwitterのリンクからもすべて参照可
		if(twitter.getId() == fussage.getUserId()) {
			return true;
		}

		switch (fussage.getScope()) {
			case 0: {
				// 自分のみ
				// ここに処理がくる場合は、公開範囲が自分のみであるにもかからわず、他人がアクセスした場合のみ

				return false;
			}
			case 2: {

				// セッションにすでに格納されていればOK
				String key = "follower_" + twitter.getId() + "_" + fussage.getUserId();
				boolean ok = false;
				if (session.getAttribute(key) != null) {
					ok = true;
				}

				if (ok == false) {
					// ターゲットとソースの関係性を取得
					Relationship relationship = twitter.showFriendship(twitter.getId(), fussage.getUserId());
					ok = relationship.isSourceFollowingTarget();
				}

				// フォロワー
				if(ok) {
					session.setAttribute(key, "1");
					return true;
				}
				else{
					return false;
				}
			}
			case 3: {

				// セッションにすでに格納されていればOK
				String key = "friend_" + twitter.getId() + "_" + fussage.getUserId();
				boolean ok = false;
				if (session.getAttribute(key) != null) {
					ok = true;
				}

				if (ok == false) {
					// ターゲットとソースの関係性を取得
					Relationship relationship = twitter.showFriendship(twitter.getId(), fussage.getUserId());
					ok = relationship.isTargetFollowingSource() && relationship.isSourceFollowingTarget();
				}


				// 相互フォロー
				if(ok) {
					session.setAttribute(key, "1");
					return true;
				}
				else{
					return false;
				}
			}
			case 4: {
				// セッションにすでに格納されていればOK
				String key = "list_" + twitter.getId() + "_" + fussage.getListId();

				if (session.getAttribute(key) != null) {
					return true;
				}

				try{

					twitter.showUserListMembership(fussage.getListId(), twitter.getId());

					session.setAttribute(key, "1");

					return true;
				}
				catch (TwitterException e) {
					// 転送
					if (e.getStatusCode() != 404) {
						throw e;
					}
				}

				try {

					// リスト取得
					ResponseList<UserList> lists = twitter.getUserListsOwnerships(twitter.getId(), 1000, -1);

					// 対象のリストIDが自分のリストIDに含まれていればOK(オーナーかどうかをチェック）
					for(UserList list:lists){
						if(list.isPublic() && list.getId() == fussage.getListId()) {
							session.setAttribute(key, "1");
							return true;
						}
					}
				}
				catch (TwitterException e) {
					throw e;
				}

				return false;
			}
			case 5: {

				// セッションにすでに格納されていればOK
				String key = "retweet_" + twitter.getId() + "_" + fussage.getStatusId();
				boolean ok = false;
				if (session.getAttribute(key) != null) {
					ok = true;
				}

				if (ok == false) {
					// 自分がリツイートしているかどうかチェック
					Paging paging = new Paging(1, 20, fussage.getStatusId() - 1, fussage.getStatusId());
					ResponseList<Status> responseList = twitter.getUserTimeline(fussage.getUserId(), paging);
					ok = responseList.size() > 0 && responseList.get(0).isRetweetedByMe();
				}


				if(ok) {
					session.setAttribute(key, "1");
					return true;
				}
				else{
					return false;
				}
			}
			case 6: {
				// @付きの人
				String contents = fussage.getTurnTweet();
				contents = contents.replaceAll("　", " ");
				contents = contents.replaceAll("\r", " ");
				contents = contents.replaceAll("\n", " ");

				contents = " " + contents  + " ";
				if(contents.toLowerCase().indexOf(" @" + twitter.getScreenName().toLowerCase() + " ") != -1) {
					return true;
				}
				else{
					return false;
				}
			}
		}

		return false;
	}
}
