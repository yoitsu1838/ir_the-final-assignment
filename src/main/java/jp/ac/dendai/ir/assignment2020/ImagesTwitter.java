package jp.ac.dendai.ir.assignment2020;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ImagesTwitter {
    private String mini_avatar_url;
    private String normal_avatar_url;
    private String bigger_avatar_url;
    private String original_avatar_url;
    private String image_url;

    public String getMini_avatar_url() {
        return this.mini_avatar_url;
    }

    public String getNormal_avatar_url() {
        return this.normal_avatar_url;
    }

    public String getBigger_avatar_url() {
        return this.bigger_avatar_url;
    }

    public String getOriginal_avatar_url() {
        return this.original_avatar_url;
    }

    public String getImage_url() {
        return this.image_url;
    }

}
