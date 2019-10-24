package com.fusetter.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fusetter.common.AppException;
import com.fusetter.common.jsonParse;
import com.fusetter.constants.TwitterCardImage;
import com.fusetter.data.Fussage;
import com.fusetter.db.FusetterUserDAO;
import com.fusetter.db.FussageDAO;
import com.fusetter.web.bean.BeanFactory;
import com.fusetter.web.bean.FussageBean;

import jp.co.shinko_1930.gypsophila.web.resource.ResourceManager;
import twitter4j.Twitter;

@RestController
public class RestFussageGetByFsgCryptogram extends AbstractRestController {

	@RequestMapping(
			path = "/FussageGetByFsgCryptogram/{FsgCryptogram}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String get(HttpServletRequest request,
			@PathVariable("FsgCryptogram") String cryptogram) {

		//TODO Tomcatの設定（SI、無限に）
		//TODO git(SVN)化
		//TODO 認証系（MUST）

		Fussage fussage;
		int scope = -1;

		//空のJSONを用意
		String json = null;

		// 公開範囲取得
		{
			FussageDAO dao = FussageDAO.createFussageByCryptogram(cryptogram);
			//dao.setUtcOffset(getUtcOffset(request)); //TODO リクエストから時間をとっているのがわからないので一旦コメントアウト
			try {
				dao.execute();
			} catch (Exception e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}

			if (dao.getFussages().length == 0) {
				throw new AppException(404, "ふせ字が見つかりません。");
			}
			fussage = dao.getFussages()[0];
			scope = fussage.getScope();

		}

		// 対象のユーザーがマイページを公開しているかどうか
		{
			FusetterUserDAO dao = new FusetterUserDAO();
			dao.setUserId(fussage.getUserId());
			try {
				dao.execute();
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			if (dao.getFusetterUser() != null && dao.getFusetterUser().isUseMyPage()) {
				request.setAttribute("useMyPage", "1");
			}
		}

		//TODO　これなにやってるか確認
		ResourceManager resourceManager = (ResourceManager) request.getServletContext().getAttribute("resourceManager");

		//TODO // キャンペーン中だったらその情報を付与する

		//TODO
		//20190219 TwitterCardImage
		fussage.setTwitterImage(TwitterCardImage.getTwitterCardImage(fussage));

		String returnJson = "";
		if (scope == 1) {
			// リダイレクト
			//TODO とりあえず認証必要なほうでチェック（ALLは本当は認証いらない）
			//showAny(request, response, fussage);
			returnJson = showOther(request, fussage);

		} else {
			// showOther用のJSONを返却する。
			returnJson = showOther(request, fussage);
		}

		//TODO　JSON変換テスト
		//TODO リストの場合
		//


		return returnJson;

	}

	/**
	 * 伏せ字ツイート参照ページを表示する（公開範囲：だれでも以外）
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 */
	private String showOther(HttpServletRequest request, Fussage fussage) {

		try {

			//TODO getLanguage(request)適当に作ったのでちゃんと使えるようにすること
			// 伏せ字情報を取得
			FussageBean fussageBean = BeanFactory.createFussageBean(request, fussage, getLanguage(request));

			request.setAttribute("fussageBean", fussageBean);

			// 伏せ字参照用ログインが必要なサーブレットでログインがされているかどうか
			//Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");


//			String verifier = request.getParameter("oauth_verifier");
//			String requestToken = request.getParameter("oauth_token");


			try {

				Twitter twitter = AuthController.createTwitter();
				twitter.getScreenName();
			} catch (Exception e) {
				//TODO 何Exceptionなのか指定したほうが良い
				//				forward("show_auth.jsp", request, response);

				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.INDENT_OUTPUT);
				String returnJson = "";

				String json = mapper.writeValueAsString(fussage);

				List<String> pickupKeysList = Arrays.asList("language", "turnTweet", "screenName");
				JSONObject resultObj = jsonParse.getValuesForKeyList(json, pickupKeysList);
				resultObj.put("show_auth", true);
				returnJson = resultObj.toString();

				return returnJson;
			}



			//TODO 認証済の場合
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			String returnJson = "";

			String json = mapper.writeValueAsString(fussage);

			List<String> pickupKeysList = Arrays.asList("language", "originalTweet", "screenName");
			JSONObject resultObj = jsonParse.getValuesForKeyList(json, pickupKeysList);
			resultObj.put("show_auth", false);
			returnJson = resultObj.toString();

			return returnJson;



			//			try {
			//				boolean b = ServletUtil.acceptTweet(twitter, fussage, request.getSession());
			//				if (b == true) {
			//					/* ■ */ log(request, Logs.U0060, "userId=" + twitter.getId(), "screenName=" + twitter
			//							.getScreenName(), "cryptogram=" + fussage.getCryptogram());
			//
			//					ServletUtil.countUpViews(request, fussage.getId());
			//
			//					// 転送
			//					forward("show.jsp", request, response);
			//
			//				} else {
			//					/* ■ */ log(request, Logs.U0061, "userId=" + twitter.getId(), "screenName=" + twitter
			//							.getScreenName(), "cryptogram=" + fussage.getCryptogram());
			//
			//					// 転送
			//					forward("show_sorry.jsp", request, response);
			//
			//				}
			//			} catch (TwitterException e) {
			//				//API上限超えたのであと○分おまちくださいページに転送
			//				if (e.getRateLimitStatus() != null && e.getRateLimitStatus().getSecondsUntilReset() > 0) {
			//					/* ■ */ log(request, Logs.U0062, "userId=" + twitter.getId(), "screenName=" + twitter
			//							.getScreenName(), "cryptogram=" + fussage.getCryptogram());
			//
			//					request.setAttribute("minutesUntilReset", String.valueOf(e.getRateLimitStatus()
			//							.getSecondsUntilReset() / 60 + 1));
			//					forward("show_limit_over.jsp", request, response);
			//				} else {
			//					throw e;
			//				}
			//			}

		} catch (Exception e) {
			//TODO
			e.printStackTrace();

		}
		return null;

	}

}
