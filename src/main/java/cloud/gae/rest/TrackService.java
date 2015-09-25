package cloud.gae.rest;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Service;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Service
public class TrackService {

    public List<Track> getAll() {
        List<Track> tracks = new ArrayList<>();
        List<TrackEntity> list = ofy().load().type(TrackEntity.class).list();
        for (TrackEntity trackEntity : list) {
            Track track = new Track();
            track.artist = trackEntity.artist;
            track.title = trackEntity.title;
            tracks.add(track);
        }
        return tracks;
    }

    public void save(Track track) {
        TrackEntity trackEntity = new TrackEntity();
        trackEntity.artist = track.artist;
        trackEntity.title = track.title;
        trackEntity.trackId = track.isrc + "_" + track.trackId;
        ofy().save().entity(trackEntity).now();
    }

}
