package youtube.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Snippet {
    private String title;
    private String channelTitle;
    private Date publishedAt;
    private Trumbnails thumbnails;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Trumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Trumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }
}
