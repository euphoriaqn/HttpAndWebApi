package youtube.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Илья on 21.10.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoId {
    private String videoId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
