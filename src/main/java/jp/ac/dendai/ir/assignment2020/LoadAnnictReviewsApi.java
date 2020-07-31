package jp.ac.dendai.ir.assignment2020;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Properties;

public class LoadAnnictReviewsApi {
    private String season = "";
    private String filter_work_id = "";
    private String jsonRaw;
    private String nowPage;
    private String nextPage;
    private ArrayList<AnnictReview> annictReviewArray = new ArrayList<>();

    public static void main(String[] args) {
        LoadAnnictReviewsApi lara = new LoadAnnictReviewsApi();
        //lara.setSeason("2020-summer");
        lara.setFilter_work_id("6081");
        lara.loadJson("1");
        for(AnnictReview annictReview : lara.getAnnictReviewArray()) {
            System.out.println("◆" + annictReview.getId() + "  [" + annictReview.getRating_overall_state() + "]");//ノーマル
            System.out.println("reviews/user/name:"+ " " + annictReview.getAnnictReviewUser().getUsername() + "");//reviews/user
            System.out.println("reviews/work/title:"  + "  " + annictReview.getWork().getTitle() + "");//reviews/work
            System.out.println("reviews/work/images/Recommended_url:"  + "  " + annictReview.getWork().getImages().getRecommended_url() + "");//reviews/work/images
            System.out.println("reviews/work/images/twitter/Image_url:" + "  " + annictReview.getWork().getImages().getImagesTwitter().getImage_url() + "");//reviews/work/images/twitter

        }
        System.out.println("データを表示しました :" + lara.getAnnictReviewArray().size());
    }

    public void loadJson(String page) {//From LoadAnnictWorksApi
        //外部ファイルから認証情報を取得
        Properties prop = new Properties();
        String file = "config.properties";
        try {
            InputStream is = new FileInputStream(file);
            prop.load(is);
            is.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //
        System.out.println("jsonを読み込んでいます。[" + page + "]ページ目");
        this.nowPage = page;
        try {
            // InputStreamの用意
            URL url = new URL("https://api.annict.com/v1/reviews?access_token="+prop.getProperty("annict_access_token")+"&per_page=50&filter_season="
                    + URLEncoder.encode(season, "UTF-8")
                    +"&filter_work_id="
                    + URLEncoder.encode(filter_work_id, "UTF-8")
                    + "&per_page=50&page="
                    + URLEncoder.encode(nowPage, "UTF-8"));
            URLConnection connection = url.openConnection();
            // 接続
            connection.connect();
            // サーバからやってくるデータをInputStreamとして取得
            InputStream inputStream = connection.getInputStream();
            // 次に inputStream を読み込む InputStreamReader のインスタンス inputStreamReader を生成
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            // さらに inputStreamReader をラップする BufferedReader のインスタンス reader を生成
            BufferedReader reader = new BufferedReader(inputStreamReader);

            jsonRaw = reader.readLine();
            System.out.println(jsonRaw);

            encodeJson();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void encodeJson() {
        //JSON変換用のクラス
        ObjectMapper mapper = new ObjectMapper();
        try {
            AnnictReviewsApi annictReviewsApi = mapper.readValue(jsonRaw, AnnictReviewsApi.class);
            nextPage = String.valueOf(annictReviewsApi.getNext_page());
            for (AnnictReview annictReview : annictReviewsApi.getReviews()) {
                annictReviewArray.add(annictReview);
            }
            System.out.println("EndOf[" + nowPage + "]" + "  Next:[" + nextPage + "]");

            if (!(annictReviewsApi.getNext_page() == 0)) {
                loadJson(nextPage);
            } else {
                System.out.println("jsonの読み込みと変換が完了しました。取得件数：" + annictReviewArray.size() + "/" + annictReviewsApi.getTotal_count());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void setSeason(String season) {
        this.season = season;
    }

    public ArrayList<AnnictReview> getAnnictReviewArray() {
        return annictReviewArray;

    }

    public void setFilter_work_id(String filter_work_id) {
        this.filter_work_id = filter_work_id;
    }

}
