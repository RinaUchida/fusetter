/**
 * (c)Shinko Technomist Co. All Rights Reserved.
 */
package com.fusetter.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * キャンペーンの情報を管理するクラス
 * 
 * @author uemura-a
 */
public class CampaignDataManager {

	private static CampaignDataManager _me = new CampaignDataManager();
	
	private CampaignDataManager() {}
	
	public static CampaignDataManager getInstance() {
		return _me;
	}
	
	/** 排他用オブジェクト */
	private Object _lockObject = new Object();
	
	private static final String WINNER_FILE = "winner.txt";
	private static final String DAILY_CHALLENGE_TEMPLATE = "'challenge_'yyyyMMdd'.txt'";
	

	/** データパスのホームフォルダ */
	private String _home;
	public void setDataPath(String home) {
		_home = home;
		
		if (new File(_home).isDirectory() == false) {
			new File(_home).mkdirs();
		}
	}
	
	/**
	 * 当選者の数を返します
	 * 
	 * @return	当選者の数
	 */
	public int getPrizeWinnerCount() throws Exception {
		
		synchronized (_lockObject) {
		
			List<String> l = loadTextFile(WINNER_FILE);
		
			return l.size();
		}
	}
	
	private String createDailyChallengeFilePath() {
		return new SimpleDateFormat(DAILY_CHALLENGE_TEMPLATE).format(new Date());
	}
	
	/**
	 * 
	 * @param twitterId
	 * @return 0:OK、1:当たっている、2:今日はおわり
	 * @throws Exception
	 */
	public int canCallenge(long twitterId) throws Exception {
		synchronized (_lockObject) {
			
			// 当たっている人に入っていたらだめ
			{
				List<String> list = loadTextFile(WINNER_FILE);
				if (list.indexOf(String.valueOf(twitterId)) > -1) {
					return 1;
				}
			}
			
			// 今日やった人に含まれていたらだめ
			{
				List<String> list = loadTextFile(createDailyChallengeFilePath());
				if (list.indexOf(String.valueOf(twitterId)) == -1) {
					return 0;
				}
				else {
					return 2;
				}
			}
		}
	}
	
	public void doChallenge(long twitterId) throws Exception {
		synchronized (_lockObject) {

			if (canCallenge(twitterId) == 0) {
				
				File file = new File(_home, createDailyChallengeFilePath());
				PrintWriter out = new PrintWriter(new FileWriter(file, true));
				out.println(String.valueOf(twitterId));
				out.close();
			}
			
		}
	}	
	
	public void win(long twitterId) throws Exception {
		synchronized (_lockObject) {
			
			// 当たっている人に入っていたらだめ
			{
				List<String> list = loadTextFile(WINNER_FILE);
				if (list.indexOf(String.valueOf(twitterId)) > -1) {
					return;
				}
			}
			
			File file = new File(_home, WINNER_FILE);
			PrintWriter out = new PrintWriter(new FileWriter(file, true));
			out.println(String.valueOf(twitterId));
			out.close();
			
		}
	}
	
	/**
	 * 当選確率を返します
	 */
	public int getWinningRate() {
		synchronized (_lockObject) {
		
			try {
				return Integer.parseInt(loadTextFile("rate.txt").get(0));
			}
			catch(Exception e) {
				return 10000;
			}
			
		}
	}

	private List<String> loadTextFile(String path) throws Exception {
		
		synchronized (_lockObject) {
			
			BufferedReader in = null;
			try {
				List<String> list = new ArrayList<String>();

				File file = new File(_home, path);
				if (file.isFile()) {
					in = new BufferedReader(new FileReader(file));
					
					String line;
					
					while ((line = in.readLine()) != null) {
						line = line.trim();
						if (line.length() > 0) {
							list.add(line.toLowerCase());
						}
					}
				}
				
				return list;
			}
			finally {
				
				if (in != null) {
					try {	
						in.close();
					}
					catch(Exception e) {}
				}
				
			}
			
			
		}
		
	}
}
