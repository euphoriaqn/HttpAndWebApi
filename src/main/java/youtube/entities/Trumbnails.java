package youtube.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Илья on 21.10.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trumbnails {
    private Medium medium;

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }
}
