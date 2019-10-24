package com.fusetter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fusetter.conf.TwitterService;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

@RestController
public class AuthController2 extends AbstractRestController {

	//TODO produces = MediaType. String
	@RequestMapping(
			path = "/auth2",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public MappingJackson2JsonView get(HttpServletRequest request, @RequestParam String r) throws Exception {
		//public String get(HttpServletRequest request, @RequestParam String r) throws Exception {

		try {

			// リファラーがあれば保存
			request.getSession().setAttribute("referer", r);

			//Twitter認証
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(TwitterService.CUSTOMER_KEY, TwitterService.CUSTOMER_SECRET);

			request.getSession().setAttribute("twitter", twitter);

			String callbackURL = "https://" + TwitterService.FQDN + request.getContextPath() + "/auth_verify";
			//String callbackURL = "https://" + TwitterService.FQDN + request.getContextPath() + "/auth_verify?r=" + r;
			//String callbackURL = "https://" + TwitterService.FQDN + request.getContextPath() + "/auth_verify?r=" + r+"?requestToken="+requestToken.getToken()+"?requestTokenSecret="+requestToken.getTokenSecret() ;

			//TODO Debug
			System.out.println("callbackURL:" + callbackURL);

			RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL);

			request.getSession().setAttribute("requestToken", requestToken);

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

}
