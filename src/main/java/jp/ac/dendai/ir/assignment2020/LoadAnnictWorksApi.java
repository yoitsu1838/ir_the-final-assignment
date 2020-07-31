package jp.ac.dendai.ir.assignment2020;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


public class LoadAnnictWorksApi {
    private String season;
    private String jsonRaw;
    private String nowPage;
    private String nextPage;
    private ArrayList<AnnictWork> annictWorkArray = new ArrayList<>();


    public static void main(String[] args) {
        LoadAnnictWorksApi latl = new LoadAnnictWorksApi();
        latl.setSeason("2020-summer");
        latl.loadJson("1");
        for (AnnictWork annictWork : latl.getAnnictWorkArray()) {
            System.out.println("◆" + annictWork.getId() + "  [" + annictWork.getTitle() + "]");//ノーマル
            System.out.println("Recommended_url: " + annictWork.getImages().getRecommended_url());//ネストされた項目1 image/
            System.out.println("facebook/Og_image_url: " + annictWork.getImages().getImagesFacebook().getOg_image_url());//ネストされた項目2 image/facebook/
            System.out.println("twitter/image_url: " + annictWork.getImages().getImagesTwitter().getImage_url());//ネストされた項目2 image/twitter/
        }


    }

    public void loadJson(String page) {
        System.out.println("jsonを読み込んでいます。[" + page + "]ページ目");
        this.nowPage = page;
        try {
            // InputStreamの用意
            URL url = new URL("https://api.annict.com/v1/works?access_token=evUnT3lwAhraTapSKpmZCmIesUfH1x60lI5zG_fpvrs&per_page=50&&filter_season="
                    + URLEncoder.encode(season, "UTF-8")
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
            //System.out.println(jsonRaw);
            //System.out.println("======================================");
            /*//json整形
            String escapeWord = "total_count";
            int endIndex = jsonRaw.indexOf(escapeWord);
            //System.out.println(escapeWord+"/"+endIndex);
            endIndex = endIndex -2;
            //System.out.println(jsonRaw);
            jsonRaw = jsonRaw.substring(9, endIndex);//[～～]ノカタチへ
            System.out.println(jsonRaw);
            //System.out.println("=======================================");*/

            System.out.println(jsonRaw);
            encodeJson();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void encodeJson() {
        //JSON変換用のクラス
        ObjectMapper mapper = new ObjectMapper();
        try {
            AnnictWorksApi annictWorksApi = mapper.readValue(jsonRaw, AnnictWorksApi.class);
            nextPage = String.valueOf(annictWorksApi.getNext_page());
            for (AnnictWork annictWork : annictWorksApi.getWorks()) {
                annictWorkArray.add(annictWork);

                /*System.out.println("◆" + annictWork.getId() + "  [" + annictWork.getTitle() + "]");//ノーマル
                System.out.println("Recommended_url: " + annictWork.getImages().getRecommended_url());//ネストされた項目1 image/
                System.out.println("facebook/Og_image_url: " + annictWork.getImages().getImagesFacebook().getOg_image_url());//ネストされた項目2 image/facebook/
                System.out.println("facebook/Og_image_url: " + annictWork.getImages().getImagesFacebook().getOg_image_url());//ネストされた項目2 image/facebook/*/
            }


            /*old
            List<AnnictWork> annictWorks  = mapper.readValue(jsonRaw, new TypeReference<List<AnnictWork>>() {});*/
            /*動作確認用*/
            /*
            for (AnnictWork annictWork  : annictWorks ) {
                System.out.println("◆"+annictWork.getId() + "  ["+annictWork.getTitle()+"]");//ノーマル
                System.out.println("Recommended_url: "+annictWork.getImages().getRecommended_url());//ネストされた項目1 image/
                System.out.println("facebook/Og_image_url: "+annictWork.getImages().getImagesFacebook().getOg_image_url());//ネストされた項目2 image/facebook/
                System.out.println("================================================================");
            }
            */
            System.out.println("EndOf[" + nowPage + "]" + "  Next:[" + nextPage + "]");
            Thread.sleep(100);

            if (!(annictWorksApi.getNext_page() == 0)) {
                loadJson(nextPage);
            } else {
                System.out.println("jsonの読み込みと変換が完了しました。取得件数：" + annictWorkArray.size() + "/" + annictWorksApi.getTotal_count());
            }

        } catch (JsonProcessingException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setSeason(String season) {
        this.season = season;
    }


    public ArrayList<AnnictWork> getAnnictWorkArray() {
        return annictWorkArray;

    }

}
