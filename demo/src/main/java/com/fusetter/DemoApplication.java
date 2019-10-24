package com.fusetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fusetter.config.WebConfig;

import jp.co.shinko_1930.gypsophila.db.DataSourceManager;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	WebConfig webConfig;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		DemoApplication app = ctx.getBean(DemoApplication.class);
		app.execStartup(args);
	}

	public void execStartup(String[] args) {
		//TODO Debug用ログ出力
		System.out.println("---READレプリカの設定を行います。---");

		//TODO ログの出力パスの設定。
		//		// ログの出力パスを設定
		//		LogWriter.getInstance().init(ce.getServletContext().getInitParameter("logPath"), ce.getServletContext().getInitParameter("errorLogPath"));
		//
		//		/* ■ */ LogWriter.getInstance().writeSystemLog(Logs.S9000);

		// MD5作成時のキー(メールアドレスのAESでの暗号化にも使用）
		System.setProperty("sks.md5key", "shinko1930pken");

		//TODO----------------------------------------------------------------------------------------
		// データソースの設定
		try {
			//TODO 前と設定ファイルが違うので書き方を変更
			//			Context initContext = new InitialContext();
			//			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			//
			//			DataSourceManager.getInstance().setReadDataSource((DataSource)envContext.lookup("jdbc/read"));
			//			DataSourceManager.getInstance().setUpdateDataSource((DataSource)envContext.lookup("jdbc/write"));

			DataSourceManager.getInstance().setReadDataSource(webConfig.readOnlyDataSource());
			DataSourceManager.getInstance().setUpdateDataSource(webConfig.updatableDataSource());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		//TODO
		// 伏せ字カウントを更新するタスクを登録
		//				_timer = new Timer(true);
		//				_timer.schedule(new FussageCountUpdateTimerTask(), 0, 1000 * 60);

		//TODO　これはサーバとフロントで分けるべきか
		// リソースマネージャの設定
		//		{
		//			ResourceManager manager = new ResourceManager();
		//			manager.setReroucePath(ce.getServletContext().getRealPath("/WEB-INF/resource/"));
		//
		//			ce.getServletContext().setAttribute("resourceManager", manager);
		//
		//		}

		//				// キャンペーン用のフォルダを設定します
		//				CampaignDataManager.getInstance().setDataPath(ce.getServletContext().getRealPath("/WEB-INF/cp/"));

	}

}
