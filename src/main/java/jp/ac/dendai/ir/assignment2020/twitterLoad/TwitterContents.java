package jp.ac.dendai.ir.assignment2020.twitterLoad;


/**
 * 定数を管理するクラス
 */
public class TwitterContents {

    // privateコンストラクタでインスタンス生成を抑止
    private TwitterContents(){};
    // 検索するトレンドワード数
    public static final int TRENDWORD_NUMBER = 2;
    // 最低リツイート数
    public static final int MIN_RETWEET_NUMBER = 150;
    // 最低リツイート数
    public static final int LOCALNUMBER_JAPAN = 23424856;
    // 処理間隔タイム
    public static final int TIME_INTERVAL = 10000;
    // リクエストで取得するTweetの数（100が最大）
    public static final int MAX_GETTWEET_NUMBER = 100;
    // 検索するページ数 max 15ページ
    public static final int SERACH_PAGE_NUMBER = 15;
}
