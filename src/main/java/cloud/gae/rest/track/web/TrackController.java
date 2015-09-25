package cloud.gae.rest.track.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.util.List;

import cloud.gae.rest.track.service.Track;
import cloud.gae.rest.track.service.TrackService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Path("/api")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @GET
    @Path("/tracks/all")
    @Produces("application/json")
    public List<Track> getAll() {
         return trackService.getAll();
    }

    @GET
    @Path("/tracks")
    @Produces("application/json")
    public List<Track> search(@QueryParam("artist") String artist,
                              @QueryParam("title") String title) {
         return trackService.search(artist, title);
    }

    @POST
    @Path("/tracks")
    @Consumes("application/json")
    public void create(Track track) {
        trackService.save(track);
    }
}
