package jp.ac.dendai.ir.assignment2020.twitterLoad;
/*
* Twitterからデータを取得するプログラムについては下記サイトのコードを引用・参考にしています。
* https://qiita.com/michiruFX105/items/d860fac602ab78772942
* MYSQLからPostgreSQLを使用する仕様へ書き換えています。
*
*
*
* */

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * 特定ワードを検索するクラス　
 */
public class WordSearch {

    //テスト
    public static void main(String[] args) throws TwitterException {
        Status retweetStatus = null;
        WordSearch wordSearchPRG = new WordSearch();
        // 検索し、DBへ格納
        String hashtag = "かぐや様";
        //String wordSearch = "#かぐや様 -filter:links -filter:replies";
        retweetStatus = wordSearchPRG.wordSearch(hashtag, 6081);
    }

    public Status wordSearch(String hashtag, int id) throws TwitterException {
        String queryWord = "#" + hashtag + " -filter:links -filter:replies exclude:retweets ";
        // 初期化
        Twitter twitter = new TwitterFactory().getInstance();
        Query query = new Query();
        // 1度のリクエストで取得するTweetの数（100が最大）
        query.setCount(TwitterContents.MAX_GETTWEET_NUMBER);
        // 検索ワードをセット
        query.setQuery(queryWord);

        // DB接続
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionDB.getConnection();
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e3) {
            e3.printStackTrace();
        }

        String getMaxIdSQL = "select min(tweet_id) as min_tweet_id from tweet2 where search_word  =?;";

        List<String>  minUntilCreateDatetimeList = new ArrayList<String>();

        // DBより、検索ワードのツイート作成日時の最小値を取得
        try(
                PreparedStatement ps = connection.prepareStatement(getMaxIdSQL)){

            ps.setString(1,queryWord);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    System.out.println("◆◆DB格納済　ツイートIDの最小値：" + rs.getString("min_tweet_id") + "◆◆");
                    minUntilCreateDatetimeList.add(rs.getString("min_tweet_id"));
                }
            };

        } catch (SQLException e2) {
            e2.printStackTrace();
        }

        // 過去にDBに登録済の場合は、クエリ検索条件に、最小ツイートIDを指定
        if(minUntilCreateDatetimeList.get(0) != null) {

            // 指定ツイートIDより前のツイートIDを取得
            query.setMaxId(Long.valueOf(minUntilCreateDatetimeList.get(0)));
            System.out.println(minUntilCreateDatetimeList.get(0) + "_JST");
        }

        // もっともリツイート数の多いツイート
        Status popularTweet = null;

        // 検索結果
        QueryResult result = null;

        // API残基
        GetAPIRestCount getAPIRestCount = new GetAPIRestCount();


        // 検索実行
        for (int i = 0; i < TwitterContents.SERACH_PAGE_NUMBER; i++) {

            // 検索実行
            try {
                result = twitter.search(query);
                System.out.println("ヒット数 : " + result.getTweets().size());
                System.out.println("ページ数 : " + i);
            } catch (TwitterException twi_e) {
                twi_e.printStackTrace();
                if (twi_e.getStatusCode() == 429) {
                    intarvalTime();
                }
                break;
            } catch (Exception ee) {
                ee.printStackTrace();
                break;
            }

            // 取得結果が0件の場合は、nullを返す
            if(result.getTweets().size() == 0) {
                return null;
            }

            // APIをたたくインターバル：３秒
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            // 検索結果を見てみる
            for (Status tweet : result.getTweets()) {

                popularTweet = tweet;
                System.out.println("◆◆◆◆◆◆検索したワード：" + queryWord + "◆◆◆◆◆◆");
                //System.out.println("ステータスID：" + popularTweet.getId()); // リツイートのときに必要
                System.out.println("本文：" + popularTweet.getText());
                // 発言したユーザ
//                System.out.println("ユーザID_num：" + popularTweet.getUser().getId());
//                System.out.println("ユーザID@：" + popularTweet.getUser().getScreenName());
                System.out.println("ユーザ名表記：" + popularTweet.getUser().getName());
                // 発言した日時
//                System.out.println("ツイート日時：" + popularTweet.getCreatedAt());
//                System.out.println("言語：" + popularTweet.getLang());
//                System.out.println("地域：" + popularTweet.getGeoLocation());
//                System.out.println("お気に入られ数:" + popularTweet.getFavoriteCount());
//                System.out.println("リツイート数:" + popularTweet.getRetweetCount());

                // insert実行

                try {

                    String sql = "INSERT INTO tweet2 VALUES ("
                            + "'" +  popularTweet.getId()  + "'" +  "," // ツイートID　これでリツイート可能
                            + "'" + popularTweet.getUser().getScreenName() + "'" + "," // ユーザID
                            + "'" + popularTweet.getUser().getName() + "'" + "," // ユーザ名
                            + "'" + popularTweet.getText() + "'" + "," // ツイート本文
                            + "'" + popularTweet.getGeoLocation() + "'" + "," // 地域
                            + "'" + popularTweet.getLang() + "'" + "," // 言語
                            + "'" + queryWord + "'" + "," // 検索ワード
                            + popularTweet.getFavoriteCount() + "," // お気に入り数
                            + popularTweet.getRetweetCount() + "," // リツイート数
                            + "'" + hashtag +"'" + "," // ハッシュタグ情報を格納
                            + "'" + id +"'" + "," // Annictのアニメ作品idを格納
                            + "'" + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(popularTweet.getCreatedAt()) + "'"   // ツイート日時
                            + ");";

                    int resultInsert = statement.executeUpdate(sql);
                    System.out.println("結果１：" + resultInsert);

                } catch (SQLException e) {
                    e.printStackTrace();
                }


                query = result.nextQuery(); // 次ページ参照
            }
        }

        // 検索した結果、該当ツイートがない場合は、NULLを返す
        if(popularTweet == null ){
            return null;
        }

        return popularTweet;
    }

    // タイム
    public void intarvalTime() {
        Date date = new Date();
        System.out.println(date.toString());
        try {
            // 60秒
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
}