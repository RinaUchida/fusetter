/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.conf;

import jp.co.shinko_1930.gypsophila.log.Log;

/**
 * @author uemura-a
 *
 */
public class Logs {

	// エラーログ
	public static Log E0001 = new Log("E0001", "サーブレットで予期せぬ例外が発生しました");

	// AuthServlet
	public static Log U0010 = new Log("U0010", "Twitterの認証ページにリダイレクトします");

	// AuthVerifyServlet
	public static Log U0020 = new Log("U0020", "Twitterの認証ページから戻りました");

	// DeleteServlet
	public static Log U0030 = new Log("U0030", "伏せ字ツイートを削除します");

	// HomeServlet
	public static Log U0040 = new Log("U0040", "ホームページを表示します");

	// PostTweetServlet
	public static Log U0050 = new Log("U0050", "マイページの伏せ字ツイートの履歴を表示します");
	public static Log U0051 = new Log("U0051", "ユーザーページの伏せ字ツイートの履歴を表示します");

	// ShowServlet
	public static Log U0060 = new Log("U0060", "伏せ字の中身のページを表示します");
	public static Log U0061 = new Log("U0061", "ごめんなさいページを表示します");
	public static Log U0062 = new Log("U0062", "API制限に引っかかりました");

	// ShowAuthServlet
//	public static Log U0070 = new Log("U0070", "ツイート参照のためにTwitterの認証ページにリダイレクトします");
// AuthServletに統合したためログが不要になりました

	// ShowAuthVerifyServlet
//	public static Log U0080 = new Log("U0080", "ツイート表示ページにリダイレクトします");
// AuthVerifyServletに統合したためログが不要になりました

	// TopServlet
	public static Log U0110 = new Log("U0110", "トップページを表示します");

	// TweetServlet
	public static Log U0120 = new Log("U0120", "伏せ字ツイートしました");

	// AgreementServlet
	public static Log U0130 = new Log("U0130", "利用規約のページを表示します");

	// CompanyServlet
	public static Log U0140 = new Log("U0140", "運営会社のページを表示します");

	// PrivacyServlet
	public static Log U0150 = new Log("U0150", "個人情報の取り扱いについてのページを表示します");

	// LogoutServlet
	public static Log U0160 = new Log("U0160", "ログアウトします");


	// LanguageServlet
	public static Log U0170 = new Log("U0170", "言語設定ページを表示します");
	public static Log U0171 = new Log("U0171", "言語を設定しました");

	// MyPageServlet
	public static Log U0200 = new Log("U0200", "MyPageを表示します");

	// TweetConfirmServlet
	public static Log U0210 = new Log("U0210", "ツイート前の確認ページを表示します");

	// CompleteServlet
	public static Log U0220 = new Log("U0220", "投稿完了ページを表示します");

	// TutorialServlet
	public static Log U0230 = new Log("U0230", "チュートリアルページを表示します");

	// TwitterAuthServlet
	public static Log U0240 = new Log("U0240", "連携アプリの認証についてのページを表示します");

	// UserListServlet
	public static Log U0250 = new Log("U0250", "ユーザーリストを取得します");

	// SetScopeServlet
	public static Log U0260 = new Log("U0260", "公開範囲を設定します");

	// EditTweetServlet
	public static Log U0270 = new Log("U0270", "伏せ字ツイートを編集します");

	// SetMypageScopeServlet
	public static Log U0280 = new Log("U0280", "マイページの公開範囲を設定します");

	// UserpageServlet
	public static Log U0290 = new Log("U0290", "UserPageを表示します");

	// ShowUserpageServlet
	public static Log U0300 = new Log("U0300", "伏せ字の中身のページを表示します");
	public static Log U0301 = new Log("U0301", "ごめんなさいページを表示します");
	public static Log U0302 = new Log("U0302", "API制限に引っかかりました");

	// SetUseViewsServlet
	public static Log U0310 = new Log("U0310", "閲覧数を使用するかどうかを設定します");

	// FavoriteServlet
	public static Log U0320 = new Log("U0320", "お気に入りのツイート一覧を表示します");

	// SimpleServlet
	public static Log U0330 = new Log("U0330", "シンプルツイートページを表示します");

	// ShopServlet
	public static Log U0340 = new Log("U0340", "ストアページを表示します");

	// FAQServlet
	public static Log U0350 = new Log("U0350", "FAQページを表示します");





	// ServletContextListenerImpl
	public static Log S9000 = new Log("S9000", "アプリケーションを起動します");
	public static Log S9001 = new Log("S9001", "アプリケーションを終了します");

	// FussageCountUpdateTimerTask
	public static Log E9010 = new Log("E9010", "伏せ字カウントの更新処理で例外が発生しました");


	public static Log U9990 = new Log("U9990", "外部サイトに移動します");


}
