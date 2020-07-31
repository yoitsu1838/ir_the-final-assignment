package jp.ac.dendai.ir.assignment2020.twitterLoad;
/*
 * Twitterからデータを取得するプログラムについては下記サイトのコードを引用・参考にしています。
 * https://qiita.com/michiruFX105/items/d860fac602ab78772942
 *
 * */

import java.util.HashMap;
import java.util.Map;

import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * API制限情報を取得　
 *
 */
public class GetAPIRestCount {

    public static void getAPIRestCount() {

        Twitter twitter = new TwitterFactory().getInstance();

        Map<String , RateLimitStatus> helpmap = new HashMap<String,RateLimitStatus>();// API制限情報を格納変数を作成
        try {
            helpmap = twitter.help().getRateLimitStatus();


            for(Map.Entry<String, RateLimitStatus> e : helpmap.entrySet()){

                if(e.getKey().equals("/application/rate_limit_status") ||
                        e.getKey().equals("/statuses/retweeters/ids") ||
                        e.getKey().equals("/search/tweets") ||
                        e.getKey().equals("/friendships/show")){
                    System.out.println(e.getKey() + "\nremain: " + e.getValue().getRemaining());
                }
            }
        } catch (TwitterException e1) {
            e1.printStackTrace();
        }
    }
}

