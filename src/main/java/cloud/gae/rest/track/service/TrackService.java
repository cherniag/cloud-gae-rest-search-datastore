package cloud.gae.rest.track.service;

import java.util.List;
import java.util.logging.Logger;

import cloud.gae.rest.track.datastore.TrackDatastoreRepository;
import cloud.gae.rest.track.datastore.TrackEntity;
import cloud.gae.rest.track.search.TrackSearchService;
import com.googlecode.objectify.VoidWork;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Service
public class TrackService {

    @Autowired
    private TrackSearchService trackSearchService;
    @Autowired
    private TrackConverter trackConverter;
    @Autowired
    private TrackDatastoreRepository repository;

    private final Logger logger = Logger.getLogger(getClass().getName());

    public List<Track> getAll() {
        List<TrackEntity> list = repository.getAll();
        return trackConverter.convertToTracks(list);
    }

    public void save(final Track track) {
        final TrackEntity trackEntity = trackConverter.convert(track);

        // work must be idempotent
        ofy().transactNew(1, new VoidWork() {
            public void vrun() {
                String key = repository.save(trackEntity);
                logger.info("Track entity is saved, key: " + key);
                trackSearchService.add(track, key);
            }
        });
    }

    public List<Track> search(String artist, String title) {
        List<String> keys = trackSearchService.search(artist, title);
        logger.info("Found keys: " + keys);

        List<TrackEntity> entities = repository.search(keys);

        return trackConverter.convertToTracks(entities);
    }



}
