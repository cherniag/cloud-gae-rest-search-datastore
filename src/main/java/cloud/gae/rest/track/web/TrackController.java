package cloud.gae.rest.track.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.util.List;
import java.util.logging.Logger;

import cloud.gae.rest.track.service.Track;
import cloud.gae.rest.track.service.TrackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Path("/api")
@Component
public class TrackController {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private TrackService trackService;

    @GET
    @Path("/tracks")
    @Produces("application/json")
    public List<Track> search(@QueryParam("artist") String artist,
                              @QueryParam("title") String title,
                              @QueryParam("page") @DefaultValue("0") Integer page,
                              @QueryParam("size") @DefaultValue("1000") Integer size) {
        logger.info("Search artist[" + artist + "], tittle [" + title + "], page " + page + ", size " + size);
        return trackService.search(artist, title, page, size);
    }

    @POST
    @Path("/tracks")
    @Consumes("application/json")
    public void create(Track track) {
        logger.info("Create " + track);
        trackService.save(track);
        logger.info("Saved");
    }

    @DELETE
    @Path("/tracks/delete")
    public void deleteData() {
        logger.info("Delete all data");
        trackService.deleteAll();
        logger.info("Deleted");
    }
}
