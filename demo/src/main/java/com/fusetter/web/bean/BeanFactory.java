/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.web.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fusetter.data.Fussage;
import com.fusetter.data.FussageArchive;

import jp.co.shinko_1930.gypsophila.web.resource.ResourceManager;

/**
 * Beanを生成するためのクラスs
 *
 * @author hata-k
 */
public class BeanFactory {

	/**
	 * FussageからFussageBeanを作成します
	 *
	 * @param fussage	Fussage
	 * @return			FussageBean
	 */
	public static FussageBean createFussageBean(HttpServletRequest request, Fussage fussage, String language) {

		//TODO 本当はリクエストからとらないといけない
		//ResourceManager resourceManager = (ResourceManager)request.getServletContext().getAttribute("resourceManager");
		ResourceManager resourceManager = new ResourceManager(); //TODO これはだめ
		resourceManager.setReroucePath("C:/workspace/demo/demo/src/main/resources/static/lang");

		FussageBean bean = new FussageBean();
		// id
		bean.setId(fussage.getId());
		// スクリーンネーム
		bean.setScreenName(fussage.getScreenName());

		// 内容
		bean.setTweet(fussage.getTweet());

		// 伏せ字内容
		bean.setTurnTweet(fussage.getTurnTweet());


		bean.setOriginalTweetWithSpan(fussage.getOriginalTweetWithSpanTag());

		// 公開範囲
		bean.setScope(fussage.getScope());

		// 公開範囲の文字列表記
		switch (fussage.getScope()) {
			case 0: {
				bean.setScopeName(resourceManager.getString(language, "common.type.myself"));
				bean.setScopeNameForTwCard(resourceManager.getString(language, "common.type.myself"));
				break;
			}
			case 1: {
				bean.setScopeName(resourceManager.getString(language, "common.type.all"));
				bean.setScopeNameForTwCard(resourceManager.getString(language, "common.type.all"));
				break;
			}
			case 2: {
				bean.setScopeName(resourceManager.getString(language, "common.type.follower"));
				bean.setScopeNameForTwCard(resourceManager.getString(language, "common.type.follower"));
				break;
			}
			case 3: {
				bean.setScopeName(resourceManager.getString(language, "common.type.friend"));
				bean.setScopeNameForTwCard(resourceManager.getString(language, "common.type.friend"));
				break;
			}
			case 4: {
				bean.setScopeName(resourceManager.getString(language, "common.type.list") + "( " + fussage.getListName() + " )");
				bean.setScopeNameForTwCard(resourceManager.getString(language, "common.type.list"));
				break;
			}
			case 5: {
				bean.setScopeName(resourceManager.getString(language, "common.type.retweet"));
				bean.setScopeNameForTwCard(resourceManager.getString(language, "common.type.retweet"));
				break;
			}
			case 6: {
				bean.setScopeName(resourceManager.getString(language, "common.type.mention"));
				bean.setScopeNameForTwCard(resourceManager.getString(language, "common.type.mention"));
				break;
			}
		}

		// リストid
		bean.setListId(fussage.getListId());
		// リスト名
		bean.setListName(fussage.getListName());
		// 識別文字列
		bean.setCryptogram(fussage.getCryptogram());

		// 登録日時
		SimpleDateFormat sd = new SimpleDateFormat(resourceManager.getString(language, "common.fussage.date_format"));
//		sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		bean.setDatetime(sd.format(fussage.getDatetime()));
		// ステータスid
		bean.setStatusId(fussage.getStatusId());

		// ノート
		bean.setNote(fussage.getNote());
		// 言語
		bean.setLanguage(fussage.getLanguage());


		bean.setImageUrl(fussage.getImageUrl());

		bean.setOriginalTweet(fussage.getOriginalTweet());

		bean.setUpdate(fussage.isUpdated());

		// 2013/8/1以前は閲覧数なし
		if (fussage.getDatetime().getTime() < 1375282800000L) {
			bean.setViews(-1);
		}
		else {
			bean.setViews(fussage.getViews());
		}

		bean.setUseViews(fussage.getUseViews());

		//20190131広告レベルを追加
		bean.setAdLevel(fussage.getAdLevel());

		//20190218 TwitterCard検証
		bean.setTwitterImage(fussage.getTwitterImage());

		return bean;
	}

	public static void main(String[] args) throws Exception {
		System.out.print(new SimpleDateFormat("yyyyMMdd").parse("20130801").getTime());
	}

	/**
	 * Fussage一覧からFussgeBean一覧を生成します
	 *
	 * @param fussages	Fussage一覧
	 * @return	FussageBean一覧
	 */
	public static FussageBean[] createFussageBeans(HttpServletRequest request, Fussage[] fussages, String language) {

		List<FussageBean> list = new ArrayList<FussageBean>();

		for (Fussage fussage : fussages) {
			list.add(createFussageBean(request, fussage, language));
		}

		return list.toArray(new FussageBean[0]);

	}


	public static FussageArchiveBean createFussageArchiveBean(HttpServletRequest request, FussageArchive obj, String language) {

		ResourceManager resourceManager = (ResourceManager)request.getServletContext().getAttribute("resourceManager");

		FussageArchiveBean bean = new FussageArchiveBean();

		bean.setLabel(new SimpleDateFormat(resourceManager.getString(language, "mypage.archive.yyyymm_format")).format(obj.getDate()));
		bean.setYyyymm(new SimpleDateFormat("yyyyMM").format(obj.getDate()));
		bean.setCount(obj.getCount());

		return bean;
	}


	public static FussageArchiveBean[] createFussageArchiveBeans(HttpServletRequest request, FussageArchive[] objs, String language) {

		ResourceManager resourceManager = (ResourceManager)request.getServletContext().getAttribute("resourceManager");

		int countAll = 0;

		List<FussageArchiveBean> list = new ArrayList<FussageArchiveBean>();

		for (FussageArchive obj : objs) {
			list.add(createFussageArchiveBean(request, obj, language));
			countAll += obj.getCount();
		}



		// トータル用
		FussageArchiveBean all = new FussageArchiveBean();
		all.setCount(countAll);
		all.setYyyymm("");
		all.setLabel(resourceManager.getString(language, "mypage.archive.all_label"));

		list.add(0, all);

		return list.toArray(new FussageArchiveBean[0]);
	}
}
