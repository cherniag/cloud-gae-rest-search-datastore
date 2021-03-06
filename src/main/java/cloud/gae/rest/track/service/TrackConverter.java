package cloud.gae.rest.track.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cloud.gae.rest.track.datastore.TrackEntity;

import org.springframework.stereotype.Service;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Service
public class TrackConverter {

    public List<Track> convertToTracks(Collection<TrackEntity> trackEntities) {
        List<Track> tracks = new ArrayList<>();
        for (TrackEntity trackEntity : trackEntities) {
            tracks.add(convert(trackEntity));
        }
        return tracks;
    }

    public List<TrackEntity> convertFromTracks(Collection<Track> tracks) {
        List<TrackEntity> trackEntities = new ArrayList<>();
        for (Track track : tracks) {
            trackEntities.add(convert(track));
        }
        return trackEntities;
    }

    public TrackEntity convert(Track track) {
        TrackEntity trackEntity = new TrackEntity();
        trackEntity.id = track.id;
        trackEntity.artist = track.artist;
        trackEntity.title = track.title;
        trackEntity.isrc = track.isrc;
        return trackEntity;
    }

    public Track convert(TrackEntity trackEntity) {
        Track track = new Track();
        track.id = trackEntity.id;
        track.artist = trackEntity.artist;
        track.title = trackEntity.title;
        track.isrc = trackEntity.isrc;
        return track;
    }

}
