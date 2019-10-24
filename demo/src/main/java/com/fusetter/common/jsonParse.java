package com.fusetter.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class jsonParse {

	//TODO List型のJsonはできないのが痛い
	public static JSONObject getValuesForKeyList(String jsonStr, List<String> returnKeys) {

		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonStr);

			JSONArray allKeys = jsonObj.names();

			List<String> keyList = new ArrayList<>();
			for (int i = 0; i < allKeys.length(); i++) {
				keyList.add(allKeys.getString(i));
			}

			for (String targetKey : keyList) {

				if (!(returnKeys.contains(targetKey))) {

					jsonObj.remove(targetKey);

				}

			}

//			jsonStr = jsonObj.toString();

		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return jsonObj;
	}

}
