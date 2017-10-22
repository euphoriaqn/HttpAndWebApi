package youtube.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Илья on 21.10.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Medium {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
