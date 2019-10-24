package com.fusetter.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fusetter.data.Fussage;

public class TwitterCardImage{
	private TwitterCardImage() {};

//	private static final String collaboHashtag = "[#＃]"+ "(ふせったー|伏せ太|fusetter|カリギュラ感想|カリギュラμ|カリギュラ主人公|佐竹笙悟|峯沢維弦|巴鼓太郎|響鍵介|柏葉琴乃|守田鳴子|篠原美笛|神楽鈴奈|琵琶坂永至|天本彩声|オルタエゴ回想)";
//	private static final String collaboHashtag = "[#＃]"+ "(ふせったー|伏せ太|fusetter|アップデートをあらしめよ)";
	private static final String collaboHashtag = "[#＃]"+ "(ふせったー|伏せ太|fusetter)";

	private static final Map<String, String> TwitterCardImageMap;

	public static final String defaultTwitterCardImage = "/r/img/twitter_card_summary.png";

	static {
		 TwitterCardImageMap = new HashMap<String, String>();
		 //ふせったー標準隠し機能①
		 TwitterCardImageMap.put("#ふせったー", "/r/img/twitter_card_summary_secret.png");
		 TwitterCardImageMap.put("＃ふせったー", "/r/img/twitter_card_summary_secret.png");

		 //ふせったー標準隠し機能②
		 TwitterCardImageMap.put("#伏せ太", "/r/img/twitter_card_summary_secret.png");
		 TwitterCardImageMap.put("＃伏せ太", "/r/img/twitter_card_summary_secret.png");

		 //ふせったー標準隠し機能③
		 TwitterCardImageMap.put("#fusetter", "/r/img/twitter_card_summary_secret.png");
		 TwitterCardImageMap.put("＃fusetter", "/r/img/twitter_card_summary_secret.png");

		 //カリギュラコラボ第２弾
//		 TwitterCardImageMap.put("#カリギュラ感想", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃カリギュラ感想", "/r/img/twittercard_carigula2.png");

		 //第２弾キャラクター別

		 //①μ
//		 TwitterCardImageMap.put("#カリギュラμ", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃カリギュラμ", "/r/img/twittercard_carigula2.png");

		 //②カリギュラ主人公
//		 TwitterCardImageMap.put("#カリギュラ主人公", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃カリギュラ主人公", "/r/img/twittercard_carigula2.png");

		 //③佐竹笙悟
//		 TwitterCardImageMap.put("#佐竹笙悟", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃佐竹笙悟", "/r/img/twittercard_carigula2.png");

		 //④峯沢維弦
//		 TwitterCardImageMap.put("#峯沢維弦", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃峯沢維弦", "/r/img/twittercard_carigula2.png");

		 //⑤巴鼓太郎
//		 TwitterCardImageMap.put("#巴鼓太郎", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃巴鼓太郎", "/r/img/twittercard_carigula2.png");

		 //⑥響鍵介
//		 TwitterCardImageMap.put("#響鍵介", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃響鍵介", "/r/img/twittercard_carigula2.png");

		 //⑦柏葉琴乃
//		 TwitterCardImageMap.put("#柏葉琴乃", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃柏葉琴乃", "/r/img/twittercard_carigula2.png");

		 //⑧守田鳴子
//		 TwitterCardImageMap.put("#守田鳴子", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃守田鳴子", "/r/img/twittercard_carigula2.png");

		 //⑨篠原美笛
//		 TwitterCardImageMap.put("#篠原美笛", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃篠原美笛", "/r/img/twittercard_carigula2.png");

		 //⑩神楽鈴奈
//		 TwitterCardImageMap.put("#神楽鈴奈", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃神楽鈴奈", "/r/img/twittercard_carigula2.png");

		 //⑪琵琶坂永至
//		 TwitterCardImageMap.put("#琵琶坂永至", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃琵琶坂永至", "/r/img/twittercard_carigula2.png");

		 //⑫天本彩声
//		 TwitterCardImageMap.put("#天本彩声", "/r/img/twittercard_carigula2.png");
//		 TwitterCardImageMap.put("＃天本彩声", "/r/img/twittercard_carigula2.png");

		 //ALTER EGOコラボキャンペーン第1弾
//		 TwitterCardImageMap.put("#オルタエゴ回想", "/r/img/twittercard_alterego.jpg");
//		 TwitterCardImageMap.put("＃オルタエゴ回想", "/r/img/twittercard_alterego.jpg");

		 //ALTER EGOコラボキャンペーン第2弾
//		 TwitterCardImageMap.put("#アップデートをあらしめよ", "/r/img/twittercard_alterego.jpg");
//		 TwitterCardImageMap.put("＃アップデートをあらしめよ", "/r/img/twittercard_alterego.jpg");


	}

	public static String getTwitterCardImage(Fussage fussage) {
		//検索する文字列を設定する
		Pattern p = Pattern.compile(collaboHashtag);
		Matcher m = p.matcher(fussage.getTurnTweet());

		if(m.find()) {
			//コラボ画像パスを設定する
			String a =m.group();
			return TwitterCardImageMap.get(a);

		}else {
			return defaultTwitterCardImage;

		}

	}

}
