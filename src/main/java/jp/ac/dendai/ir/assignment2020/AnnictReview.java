package jp.ac.dendai.ir.assignment2020;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AnnictReview {
    private int id;   // ID
    private String title;
    private String body;
    private String rating_animation_state;
    private String rating_music_state;
    private String rating_story_state;
    private String rating_character_state;
    private String rating_overall_state;
    private String likes_count;
    private String impressions_count;
    private String created_at;
    private String modified_at;
    private AnnictReviewUser user;
    private AnnictWork work;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getRating_animation_state() {
        return rating_animation_state;
    }

    public String getRating_music_state() {
        return rating_music_state;
    }

    public String getRating_story_state() {
        return rating_story_state;
    }

    public String getRating_character_state() {
        return rating_character_state;
    }

    public String getRating_overall_state() {
        return rating_overall_state;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public String getImpressions_count() {
        return impressions_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getModified_at() {
        return modified_at;
    }

    public AnnictReviewUser getAnnictReviewUser() {
        return user;
    }

    public AnnictWork getWork() {
        return work;
    }




}
