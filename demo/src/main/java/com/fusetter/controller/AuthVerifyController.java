package com.fusetter.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fusetter.common.ServletUtil;
import com.fusetter.conf.TwitterService;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

@Controller()
public class AuthVerifyController extends AbstractRestController {

	//TODO produces = MediaType. String
	@RequestMapping(
			path = "/auth_verify2",
			method = RequestMethod.GET)
	//	public String get(HttpServletRequest request, @RequestParam String r) throws Exception {
	public String get(HttpServletRequest request) throws Exception {

		// リファラー取得
		String referer = (String) request.getSession().getAttribute("referer");
		//		String referer = r;

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

		//RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");

		//TODO HttpServletRequestで渡せないので何か考えないと。

		String verifier = request.getParameter("oauth_verifier");
		String requestToken = request.getParameter("oauth_token");

		//TODO verifierだけで行けるのか -> Token必要
		AccessToken accessToken = twitter.getOAuthAccessToken(verifier);

		//TODO Java内でアクセストークンは渡せない？
		//AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);


		//		/* ■ */ log(request, Logs.U0020, "userId=" + twitter.getId(), "screenName=" + twitter.getScreenName());

		// DB更新
		User user = twitter.showUser(twitter.getId());
		ServletUtil.updateUser(request, user, getLanguage(request));


		request.getSession().setAttribute("accessToken", accessToken);

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

}
