package jp.ac.dendai.ir.assignment2020;

import java.util.ArrayList;

public class CalcAnnictReviewRating {
    private int reviewNum;
    private int invailReviewNum;
    private String overallRawRating;
    private int sum;
    private int overallIntRating;
    private float result;
    ArrayList<AnnictReview> annictReviews = new ArrayList<>();

    public static void main(String[] args) {
        CalcAnnictReviewRating calcAnnictReviewRating = new CalcAnnictReviewRating();
        calcAnnictReviewRating.runCalc("7611");
    }

    public void runCalc(String workId) {
        sum = 0;
        invailReviewNum =0;
        String id = workId;
        LoadAnnictReviewsApi lara = new LoadAnnictReviewsApi();
        lara.setFilter_work_id(id);
        lara.loadJson("1");
        annictReviews = lara.getAnnictReviewArray();
        reviewNum = annictReviews.size();
        System.out.println("");
        for (AnnictReview annictReview : annictReviews) {
            overallRawRating = annictReview.getRating_overall_state();
            sum += exchangeRatingInt(overallRawRating);
           // System.out.println("足し算実行 sum ="+sum);
        }
        if (reviewNum-invailReviewNum==0){
            result =0;
        }else {
            result = sum / (reviewNum - invailReviewNum);//レーティングの入力がないものを分母から除く
        }
        System.out.println("計算結果"+result);

    }

    public float getReviewScore() {
        return result;
    }

    private int exchangeRatingInt(String rawRating) {


        if (!(rawRating==null)) {
            if (rawRating.equals("great")) {
                overallIntRating = 4;
            } else if (rawRating.equals("good")) {
                overallIntRating = 3;
            } else if (rawRating.equals("average")) {
                overallIntRating = 2;
            } else if (rawRating.equals("bad")) {
                overallIntRating = 1;
            }
        }else{
            overallIntRating = 0;
            invailReviewNum ++;

        }


        return overallIntRating;

    }
}
