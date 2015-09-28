package cloud.gae.rest.track.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Unindex;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Entity
public class TrackEntity {
    @Id
    public String id;
    @Unindex
    public String title;
    @Unindex
    public String artist;
    @Unindex
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
