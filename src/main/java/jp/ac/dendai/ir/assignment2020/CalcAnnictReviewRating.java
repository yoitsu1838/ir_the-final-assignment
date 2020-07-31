package jp.ac.dendai.ir.assignment2020;

public class CalcAnnictReviewRating {
    public static void main(String[] args) {
        CalcAnnictReviewRating calcAnnictReviewRating = new CalcAnnictReviewRating();
        calcAnnictReviewRating.runCalc("6081");
    }

    public void runCalc(String workId){
        String id = workId;
        LoadAnnictReviewsApi lara = new LoadAnnictReviewsApi();
        lara.setFilter_work_id(id);
        lara.loadJson("1");
    }
}
