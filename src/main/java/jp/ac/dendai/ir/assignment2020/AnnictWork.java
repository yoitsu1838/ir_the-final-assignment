package jp.ac.dendai.ir.assignment2020;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AnnictWork {
    private int id;   // ID
    private String title;
    private String title_kana;
    private String media;
    private String media_text;
    private String released_on;
    private String released_on_about;
    private String official_site_url;
    private String wikipedia_url;
    private String syobocal_tid;
    private String mal_anime_id;
    private AnnictWorkImages images;//nest
    private int episodes_count;
    private int watchers_count;
    private int reviews_count;
    private boolean no_episodes;
    private String season_name;
    private String season_name_text;


    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTitle_kana() {
        return this.title_kana;
    }

    public String getMedia() {
        return this.media;
    }

    public String getMedia_text() {
        return this.media_text;
    }

    public String getReleased_on() {
        return this.released_on;
    }

    public String getReleased_on_about() {
        return this.released_on_about;
    }

    public String getOfficial_site_url() {
        return this.official_site_url;
    }

    public String getWikipedia_url() {
        return this.wikipedia_url;
    }

    public String getSyobocal_tid() {
        return this.syobocal_tid;
    }

    public String getMal_anime_id() {
        return this.mal_anime_id;
    }

    public AnnictWorkImages getImages() { //nest
        return this.images;
    }

    public int getEpisodes_count() {
        return this.episodes_count;
    }

    public int getWatchers_count() {
        return this.watchers_count;
    }

    public int getReviews_count() {
        return this.reviews_count;
    }

    public boolean getNo_episodes() {
        return this.no_episodes;
    }

    public String getSeason_name() {
        return this.season_name;
    }

    public String getSeason_name_text() {
        return this.season_name_text;
    }


}
