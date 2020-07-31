package jp.ac.dendai.ir.assignment2020.twitterLoad;



import java.util.Date;

import twitter4j.Status;
import twitter4j.TwitterException;
/**
 * 任意のワードを検索し、過去分のツイートをDBに格納するResourceクラス
 */
public class SearchWordResource {

    public static void main(String[] args) {




            WordSearch wordSearchPRG = new WordSearch();
            Status retweetStatus = null;

            try {
                // 検索し、DBへ格納
                String wordSearch = "働き方改革";
                retweetStatus = wordSearchPRG.wordSearch(wordSearch);

            } catch (TwitterException e) {
                e.printStackTrace();
            }

            // sleep
            try {
                Date date = new Date();
                System.out.println(date.toString());
                Thread.sleep(TwitterContents.TIME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}