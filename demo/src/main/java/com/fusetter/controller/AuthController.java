package com.fusetter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fusetter.common.ServletUtil;
import com.fusetter.conf.TwitterService;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@Controller
@RequestMapping("")
public class AuthController extends AbstractRestController {

	@Autowired
	private HttpSession session;
	@Autowired
	private HttpServletRequest request;

	private RequestToken requestTokenBk;
	private static AccessToken accessTokenBK;

	//TODO produces = MediaType. String
	@RequestMapping(
			path = "/auth",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public MappingJackson2JsonView auth(@RequestParam String r) throws Exception {
		//public String get(HttpServletRequest request, @RequestParam String r) throws Exception {

		try {

			// リファラーがあれば保存
			request.getSession().setAttribute("referer", r);

			//Twitter認証
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(TwitterService.CUSTOMER_KEY, TwitterService.CUSTOMER_SECRET);

			request.getSession().setAttribute("twitter", twitter);

			//String callbackURL = "https://" + TwitterService.FQDN + request.getContextPath() + "/auth_verify";
			String callbackURL = "https://" + TwitterService.FQDN + request.getContextPath() + "/auth_verify?r=" + r;
			//String callbackURL = "https://" + TwitterService.FQDN + request.getContextPath() + "/auth_verify?r=" + r+"?requestToken="+requestToken.getToken()+"?requestTokenSecret="+requestToken.getTokenSecret() ;

			//TODO Debug
			System.out.println("callbackURL:" + callbackURL);

			RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL);

			//			request.getSession().setAttribute("requestToken", requestToken);
			session.setAttribute("requestToken", requestToken);
			requestTokenBk = requestToken;

			//TODO これなんだろう
			boolean changeAccount = (request.getParameter("a") != null);

			//TODO ログは保留
			//		    /* ■ */ log(request, Logs.U0010, "changeAccount=" + changeAccount, "referer=" + referer);

			if (changeAccount == false) {

				//TODO やり方を変えた
				//response.sendRedirect(requestToken.getAuthenticationURL());
				//TODO
				//return requestToken.getAuthenticationURL();
				//return requestToken.getAuthenticationURL();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("URL", requestToken.getAuthenticationURL());
				map.put("referer", r.toString());
				map.put("requestToken", requestToken.getToken());
				map.put("requestTokenSecret", requestToken.getTokenSecret());
				//map.put("twitter", twitter);
				MappingJackson2JsonView view = new MappingJackson2JsonView();
				view.setAttributesMap(map);
				return view;

				//return "redirect:" + requestToken.getAuthenticationURL();

			} else {
				// アカウント切り替え
				//response.sendRedirect(requestToken.getAuthenticationURL() + "&force_login=true");
			}

			return null;

		} catch (Exception e) {

			//TODO とりあえず適用
			throw new Exception(e);

		}

	}

	//TODO produces = MediaType. String
	@RequestMapping(
			path = "/auth_verify",
			method = RequestMethod.GET)
	//	public String get(HttpServletRequest request, @RequestParam String r) throws Exception {
	@CrossOrigin
	public String auth_verify(@RequestParam String r) throws Exception {

		// リファラー取得
		//String referer = (String) request.getSession().getAttribute("referer");
		String referer = r;

		// Twitterの認証をキャンセルした場合は、トップにリダイレクトする
		if (request.getParameter("denied") != null) {
			if (referer != null) {
				//				response.sendRedirect(referer);
				return referer;
			} else {
				//response.sendRedirect(request.getContextPath() + "/");
				return "/";
			}
		}

		//TODO セッション受け渡しはできない？
		//Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
		//Twitter認証
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TwitterService.CUSTOMER_KEY, TwitterService.CUSTOMER_SECRET);

		RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");
		requestToken = requestTokenBk;

		//TODO HttpServletRequestで渡せないので何か考えないと。

		String verifier = request.getParameter("oauth_verifier");
		//		String requestToken = request.getParameter("oauth_token");

		//TODO verifierだけで行けるのか -> Token必要
		//AccessToken accessToken = twitter.getOAuthAccessToken(verifier);

		//TODO Java内でアクセストークンは渡せない？
		AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

		//		/* ■ */ log(request, Logs.U0020, "userId=" + twitter.getId(), "screenName=" + twitter.getScreenName());

		// DB更新
		User user = twitter.showUser(twitter.getId());
		ServletUtil.updateUser(request, user, getLanguage(request));

		request.getSession().setAttribute("accessToken", accessToken);
		accessTokenBK = accessToken;

		//		// キャンペーン情報があれば復活する
		//					if (request.getSession().getAttribute("campaign-backup") != null) {
		//						request.getSession().setAttribute("campaign", request.getSession().getAttribute("campaign-backup"));
		//						request.getSession().setAttribute("campaign-backup", null);
		//					}

		// リファラーがあればそこに戻す
		if (referer != null) {
			//			response.sendRedirect(referer);
			//TODO 戻せない
			//http://localhost:8080/tw/LiiTS

			//return referer;

			System.out.println("redirect:" + "http://localhost:8080" + referer);
			return "redirect:" + "http://localhost:8080" + referer;
		} else {
			//TODO 適当
			//			response.sendRedirect(request.getContextPath() + "/home");
			return "/";
		}
	}

	//TODO 適当
	@RequestMapping(
			path = "/logout",
			method = RequestMethod.GET)
	//	public String get(HttpServletRequest request, @RequestParam String r) throws Exception {
	@CrossOrigin(origins = "*", maxAge = 3600)
	public String logout(@RequestParam String r) throws Exception {

		accessTokenBK = null;

		// リファラー取得
		//String referer = (String) request.getSession().getAttribute("referer");
		String referer = r;
		System.out.println("redirect:" + "http://localhost:8080" + referer);
		return "redirect:" + "http://localhost:8080" + referer;

	}

	public static Twitter createTwitter() {
		//        AccessToken accessToken = accessTokenBK;
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(TwitterService.CUSTOMER_KEY);
		builder.setOAuthConsumerSecret(TwitterService.CUSTOMER_SECRET);
		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		twitter.setOAuthAccessToken(accessTokenBK);

		return twitter;
	}

}
