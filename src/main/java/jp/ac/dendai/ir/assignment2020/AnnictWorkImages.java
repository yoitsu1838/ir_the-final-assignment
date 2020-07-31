package jp.ac.dendai.ir.assignment2020;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class AnnictWorkImages {
    private String recommended_url;
    private ImagesFacebook facebook;
    private ImagesTwitter twitter;

    public String getRecommended_url() {
        return recommended_url;
    }

    public ImagesFacebook getImagesFacebook() {
        return facebook;
    }
    public ImagesTwitter getImagesTwitter() {
        return twitter;
    }
}
