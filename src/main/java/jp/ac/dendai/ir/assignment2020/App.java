package jp.ac.dendai.ir.assignment2020;

import jp.ac.dendai.ir.assignment2020.twitterLoad.WordSearch;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.HashMap;

public class App 
{
    public static void main(String[] args) throws TwitterException {
        int id;
        String workTitle, hashtag;

        /*作品一覧とハッシュタグの取得*/
        ArrayList<AnnictWork> annictWorkArray;
        ArrayList<AnnictWork> existHashtagWorks = new ArrayList<>();

        LoadAnnictWorksApi loadAnnictWorksApi = new LoadAnnictWorksApi();
        LoadAnnictReviewsApi loadAnnictReviewsApi = new LoadAnnictReviewsApi();

        loadAnnictWorksApi.setSeason("2020-summer");
        loadAnnictWorksApi.loadJson("1");
        annictWorkArray = loadAnnictWorksApi.getAnnictWorkArray();

        //ハッシュタグがないものをはじく
        for(AnnictWork annictWork : annictWorkArray) {
            id =annictWork.getId();
            workTitle = annictWork.getTitle();
            hashtag = annictWork.getTwitter_hashtag();

            System.out.println("◆" + id + "  [" + workTitle + "]");//ノーマル
            System.out.println("ハッシュタグ: " + hashtag );//ネストされた項目1 image/
            if(!(annictWork.getTwitter_hashtag().length()==0)){
                System.out.println("ハッシュタグが登録されているため，existHashtagWorksに追加");
                existHashtagWorks.add(annictWork);
            }
        }
        //ハッシュタグありのみかの動作確認
        for (AnnictWork work : existHashtagWorks) {
            System.out.println("★★★"+ work.getTitle());
            System.out.println("#"+work.getTwitter_hashtag());
        }


        /*　ハッシュタグからツイートを取得し，データベースへ格納　*/
        Status retweetStatus = null;
        WordSearch wordSearchPRG;
        int loadTwCount = 0;
        for (AnnictWork work : existHashtagWorks) {
            if (loadTwCount==3){//デモ・動作チェックのため３つまで
                break;
            }
            wordSearchPRG = new WordSearch();
            retweetStatus = wordSearchPRG.wordSearch(work.getTwitter_hashtag());
            loadTwCount++;
        }


        /*　評価実行　*/
        //Annictのレビューデータを数値化

    }
}
