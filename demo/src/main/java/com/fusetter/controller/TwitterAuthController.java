package com.fusetter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
@Controller
@RequestMapping("")
public class TwitterAuthController {
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("requestToken")
    public String requestToken(Model model) throws TwitterException{
        Configuration conf = ConfigurationContext.getInstance();
        OAuthAuthorization oauth = new OAuthAuthorization(conf);
        String callbackURL = "https://fusetter.com/accessToken";
        RequestToken requestToken = oauth.getOAuthRequestToken(callbackURL);

        session.setAttribute("requestToken", requestToken);

        return "redirect:" + requestToken.getAuthenticationURL();
    }

    @RequestMapping("accessToken")
    public String accessToken(Model model) throws TwitterException{
        Configuration conf = ConfigurationContext.getInstance();
        RequestToken requestToken = (RequestToken)session.getAttribute("requestToken");
        AccessToken accessToken = new AccessToken(requestToken.getToken(),requestToken.getTokenSecret());
        OAuthAuthorization oauth = new OAuthAuthorization(conf);

        oauth.setOAuthAccessToken(accessToken);
        String verifier = request.getParameter("oauth_verifier");
        accessToken = oauth.getOAuthAccessToken(verifier);

        session.setAttribute("accessToken", accessToken);

        return "redirect:mypage";
    }
}