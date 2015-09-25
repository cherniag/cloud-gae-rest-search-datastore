package cloud.gae.rest.track.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Entity
public class TrackEntity {
    @Id
    public String trackId;
    public String title;
    public String artist;


    @Override
    public String toString() {
        return "TrackEntity{" +
               "trackId='" + trackId + '\'' +
               ", title='" + title + '\'' +
               ", artist='" + artist + '\'' +
               '}';
    }
}
