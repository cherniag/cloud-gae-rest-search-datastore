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

    public void save(final Track track) {
        final TrackEntity trackEntity = trackConverter.convert(track);

        // work must be idempotent
        ofy().transactNew(1, new VoidWork() {
            public void vrun() {
                long now = System.currentTimeMillis();
                String key = repository.save(trackEntity);
                logger.info("Track entity is saved, key: " + key);
                trackSearchService.add(track, key);
                logger.info("Create record took " + (System.currentTimeMillis() - now) + " ms");
            }
        });
    }

    public List<Track> search(String artist, String title, Integer page, Integer size) {
        long now = System.currentTimeMillis();

        List<String> keys = trackSearchService.search(artist, title, page, size);

        logger.info("Found keys: " + keys + " in " + (System.currentTimeMillis()- now) + " ms");

        List<TrackEntity> entities = repository.search(keys);

        logger.info("Search by Search API overall took " + (System.currentTimeMillis()- now) + " ms");
        return trackConverter.convertToTracks(entities);
    }


    public void deleteAll() {
        repository.deleteAll();
        trackSearchService.deleteAll();
    }
}
