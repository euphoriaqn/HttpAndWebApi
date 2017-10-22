package youtube.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    private VideoId id;

    public VideoId getId() {
        return id;
    }

    public void setId(VideoId id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return getSippet;
    }

    public void setSnippet(Snippet snippet) {
        this.getSippet = snippet;
    }

    private Snippet getSippet;
}
