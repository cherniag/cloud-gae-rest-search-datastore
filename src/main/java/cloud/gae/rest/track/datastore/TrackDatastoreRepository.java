package cloud.gae.rest.track.datastore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Service;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Service
public class TrackDataStoreRepository {
    private final Logger logger = Logger.getLogger(getClass().getName());

    public String save(TrackEntity trackEntity) {
        return ofy().save().entity(trackEntity).now().getName();
    }

    public TrackEntity findOne(String id) {
        return ofy().load().key(Key.create(TrackEntity.class, id)).now();
    }

    public List<TrackEntity> search(List<String> keys) {

        List<Key<TrackEntity>> k = toKeys(keys);

        Collection<TrackEntity> values = ofy().load().keys(k).values();
        logger.info("Found TrackEntities: " + values);

        return new ArrayList<>(values);
    }

    public void deleteAll() {
        while(true) {
            List<Key<TrackEntity>> list = ofy().load().type(TrackEntity.class).limit(10000).keys().list();
            logger.info("fetched " + list.size() + " keys to delete");
            if(list.isEmpty()){
                return;
            }
            ofy().delete().keys(list);
        }
    }

    private List<Key<TrackEntity>> toKeys(List<String> keys) {
        List<Key<TrackEntity>> k = new ArrayList<>();
        for (String key : keys) {
            k.add(Key.create(TrackEntity.class, key));
        }
        return k;
    }
}
