package jp.ac.dendai.ir.assignment2020;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AnnictReviewsApi {

    private List<AnnictReview> reviews;
    private int total_count;
    private int next_page;
    private int prev_page;

    public List<AnnictReview> getReviews() {
        return this.reviews;
    }

    public int getTotal_count() {
        return this.total_count;
    }

    public int getNext_page() {
        return this.next_page;
    }

    public int getPrev_page() {
        return this.prev_page;
    }

}
