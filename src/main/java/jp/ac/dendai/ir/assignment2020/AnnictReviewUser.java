package jp.ac.dendai.ir.assignment2020;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AnnictReviewUser {
    private String id;
    private String username;
    private String name;
    private String description;
    private String url;
    private String avatar_url;
    private String background_image_url;
    private String records_count;
    private String created_at;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getBackground_image_url() {
        return background_image_url;
    }

    public String getRecords_count() {
        return records_count;
    }

    public String getCreated_at() {
        return created_at;
    }
}
