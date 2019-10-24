package com.fusetter.controller;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractRestController {


	/**
	 * 言語設定を返します
	 */
	public String getLanguage(HttpServletRequest request) {

		//TODO 言語関係の設定調べないとな
//		return "Javanese";
//		return "default";
		return "ja";
//
//		HttpSession session = request.getSession();
//		if (session != null) {
//			synchronized (session) {
//
//				String lang = (String)session.getAttribute("language");
//				if (lang != null && lang.length() > 0) {
//					return lang;
//				}
//
//			}
//		}
//
//		return null;

	}


}
