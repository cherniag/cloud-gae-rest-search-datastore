package cloud.gae.rest.track.service;

import java.io.Serializable;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
public class Track implements Serializable {
    public String id;
    public String title;
    public String artist;
    public String isrc;

    @Override
    public String toString() {
        return "Track{" +
               "title='" + title + '\'' +
               ", artist='" + artist + '\'' +
               ", isrc='" + isrc + '\'' +
               '}';
    }
}
