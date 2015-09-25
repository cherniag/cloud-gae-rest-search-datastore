package cloud.gae.rest.track.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Entity
public class TrackEntity {
    @Id
    public String id;
    public String title;
    public String artist;
    public String isrc;


    @Override
    public String toString() {
        return "TrackEntity{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               ", artist='" + artist + '\'' +
               ", isrc='" + isrc + '\'' +
               '}';
    }
}
