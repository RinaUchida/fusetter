package com.fusetter.constants;

import java.util.HashMap;
import java.util.Map;

import com.fusetter.data.FusetterUser;

public class CollaboUserPageInformation{
	private CollaboUserPageInformation() {};

//	private static final String collaboScreanName = "fusekuma";
//	private static final String collaboScreanName = "date_maki";
	private static final String collaboScreanName = "";

	private static final Map<String, String> collaboUserMap;

	public static final String defaultPage = "noCollabo";

	static {
		collaboUserMap = new HashMap<String, String>();

		//コラボアカウントとコラボページ
		collaboUserMap.put(collaboScreanName, "_collabo_userpage_header.jsp");
	}

	public static String getCollaboPage(FusetterUser user) {


		String s = (String)collaboUserMap.get(user.getScreenName());

		return (s == null) ? defaultPage : s;

	}

//	public static void main(String[] args) throws Exception {
//
//		test("aaa", "noCollabo");
//		test("fusekuma", "_collabo_userpage_header.jsp");
//		test(null, "noCollabo");
//		test("fusekuma2", "noCollabo");
//
//	}
//
//	static void test(String scrName, String value) {
//		{
//			FusetterUser user = new FusetterUser();
//			user.setScreenName(scrName);
//			String page = CollaboUserPageInformation.getCollaboPage(user);
//			if (value == null) {
//				System.out.println(scrName + " , " + (page == null));
//			}
//			else {
//				System.out.println(scrName + " , " + (value.equals(page)));
//			}
//		}
//
//	}

}
