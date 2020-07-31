package jp.ac.dendai.ir.assignment2020.twitterLoad;
//java ライブラリ
import java.util.List;
//twitter4j ライブラリ
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.*;

public class Search {
    // Consumer と AccessToken をここで指定
    public static final String CONSUMERKEY = "自分のCONSUMERKEY";
    public static final String CONSUMERSECRET = "自分のCONSUMERSECRET";
    public static final String TOKEN = "自分のTOKEN";
    public static final String TOKENSECRET = "自分のTOKENSECRET";
    public static void main(String[] args) {
        try {
            //自動的に認証してくれる
            //（バージョン2.2.4以降はgetInstance()ではなくgetSingleton()を推奨）
            Twitter twitter = new TwitterFactory().getInstance();

            //検索語を指定する（※ここでいろいろとオプションを設定できる）
            Query query = new Query();
            query.setQuery("???");// "???"が含まれているツイート取得
            query.setLang("ja");// ユーザが設定した使用言語

            //検索結果を取得
            QueryResult result = twitter.search(query);
           // List<status> tresult = result.getTweets();

            //ツイートの表示
            //for (Status tweet : tresult) {
              //  System.out.println("@" + tweet.getUser().getScreenName() + ":" + tweet.getText());
            //}
        } catch(TwitterException te){
            te.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
