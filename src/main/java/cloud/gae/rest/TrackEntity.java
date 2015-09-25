package cloud.gae.rest;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Entity
public class TrackEntity {
    @Id
    String trackId;
    String title;
    String artist;
}
