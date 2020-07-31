package jp.ac.dendai.ir.assignment2020;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class ImagesFacebook {
    private String og_image_url;

    public String getOg_image_url() {
        return og_image_url;
    }
}
