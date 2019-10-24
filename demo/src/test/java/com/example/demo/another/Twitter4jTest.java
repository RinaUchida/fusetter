/**
 *
 */
package com.example.demo.another;

import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

/**
 * @author M15614
 *
 */
public class Twitter4jTest {

	public static final String CUSTOMER_KEY = "UA1ZT6yqgEIQcYHEhWstdg";

	public static final String CUSTOMER_SECRET = "vYJ3zYre1K7CE9XhpUnxiuuVdQkJiD89n64uF2no";

	public static final int TOTE_PRESENT_COUNT = 30;

	public static final long FUSETTER_USER_ID = 423224853L;

	@Test
	public void test() {

		try {

			Twitter twitter = new TwitterFactory().getInstance();

			// アプリケーションのconsumer key
			// アプリケーションのconsumer secret
			twitter.setOAuthConsumer(CUSTOMER_KEY,CUSTOMER_SECRET);

			//Twitterオブジェクトにアプリケーションのconsumer keyとconsumer secretをセットする


			/*
			RequestToken requestToken = twitter.getOAuthRequestToken();
		    String url = requestToken.getAuthorizationURL();

		    //アクセストークンを出力
		    System.out.println("requestToken:"+requestToken.toString());
		    System.out.println("url:"+url.toString());
			 */

		    /*
		     *
		     requestToken:OAuthToken{token='uLnw9wAAAAAAFatKAAABbc0NJfw', tokenSecret='VfVvQ01IEOEe66occz6MD8C6BKgA3NXx', secretKeySpec=null}
			 url:https://api.twitter.com/oauth/authorize?oauth_token=uLnw9wAAAAAAFatKAAABbc0NJfw



			 https://fusetter.com/auth_verify?oauth_token=uLnw9wAAAAAAFatKAAABbc0NJfw&oauth_verifier=SRhPJ39S4RqpCtgOD8CSvrWAVaaS0VM8
		     *
		     */

			String ACCESS_TOKEN = "uLnw9wAAAAAAFatKAAABbc0NJfw";

			String ACCESS_TOKEN_KEY = "SRhPJ39S4RqpCtgOD8CSvrWAVaaS0VM8";


			// 自分のAccess token
			// 自分のAccess token secret
			AccessToken accessToken = new AccessToken(ACCESS_TOKEN,ACCESS_TOKEN_KEY);

			//自分のアクセストークンを作成し、Twitterオブジェクトにセットする。
			twitter.setOAuthAccessToken(accessToken);

			User user = twitter.verifyCredentials();

			//ユーザ情報取得
			System.out.println("なまえ　　　：" + user.getName());
			System.out.println("ひょうじ名　：" + user.getScreenName());
			System.err.println("ふぉろー数　：" + user.getFriendsCount());
			System.out.println("ふぉろわー数：" + user.getFollowersCount());

		} catch (Exception e) {

			//Nothing

		}

	}

}
