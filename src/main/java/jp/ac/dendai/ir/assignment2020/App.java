package jp.ac.dendai.ir.assignment2020;

import jp.ac.dendai.ir.assignment2020.twitterLoad.WordSearch;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class App {
    public static void main(String[] args) throws TwitterException {
        int id;
        String workTitle, hashtag;

        /*作品一覧とハッシュタグの取得*/
        ArrayList<AnnictWork> annictWorkArray;
        ArrayList<AnnictWork> existHashtagWorks = new ArrayList<>();
        LoadAnnictWorksApi loadAnnictWorksApi = new LoadAnnictWorksApi();

        /* ===============取得する時期を指定する================= */ //Todo Scanner
        loadAnnictWorksApi.setSeason("2020-summer");
        /* ================================================== */
        loadAnnictWorksApi.loadJson("1");
        annictWorkArray = loadAnnictWorksApi.getAnnictWorkArray();

        //ハッシュタグがないものと評価(レビュー)がないものをはじく
        for (AnnictWork annictWork : annictWorkArray) {
            id = annictWork.getId();
            workTitle = annictWork.getTitle();
            hashtag = annictWork.getTwitter_hashtag();

            System.out.println("◆" + id + "  [" + workTitle + "]");//ノーマル
            System.out.println("ハッシュタグ: " + hashtag);//ネストされた項目1 image/

            //評価(レビューの件数を取得)
            LoadAnnictReviewsApi lara = new LoadAnnictReviewsApi();
            lara.setFilter_work_id(String.valueOf(id));
            lara.loadJson("1");
            int totalReview = lara.getTotalCount();

            if (!((annictWork.getTwitter_hashtag().length() == 0) || (totalReview == 0))) {
                System.out.println("ハッシュタグが登録されているため，existHashtagWorksに追加");
                existHashtagWorks.add(annictWork);
            }
        }
        //ハッシュタグありのみかの動作確認
        for (AnnictWork work : existHashtagWorks) {
            System.out.println("★★★" + work.getTitle());
            System.out.println("#" + work.getTwitter_hashtag());
        }


        //Annictのレビューデータを数値化,評価は存在しても全体の評価値がNULLのため結果が０であったものはここではじいている。
        System.out.println("評価を実行します。");
        CalcAnnictReviewRating calcAnnictReviewRating = new CalcAnnictReviewRating();
        HashMap<Integer, Float> workRating = new HashMap<Integer, Float>();
        float scoreTmp;
        int count = 0;
        Iterator<AnnictWork> it = existHashtagWorks.iterator();
        while (it.hasNext()) {
            AnnictWork i = it.next();

            id = i.getId();
            System.out.println("APP:id:" + id + " タイトル：" + i.getTitle());

            calcAnnictReviewRating.runCalc(String.valueOf(id));
            scoreTmp = calcAnnictReviewRating.getReviewScore();
            if (scoreTmp == 0) {
                it.remove();
                System.out.println("deleted!! id" + id);
            } else {
                workRating.put(id, scoreTmp);
            }
        }


        for (AnnictWork work : existHashtagWorks) {
            id = work.getId();
            System.out.println("[" + work.getTitle() + "]の平均Annictスコア:" + workRating.get(id));
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
            retweetStatus = wordSearchPRG.wordSearch(work.getTwitter_hashtag(),work.getId());
            loadTwCount++;
        }



    }
}
