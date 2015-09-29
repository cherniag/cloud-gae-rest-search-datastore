package cloud.gae.rest.track.web;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Logger;

import cloud.gae.rest.track.search.TrackSearchDto;
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
    public List<Track> search(@BeanParam TrackSearchDto dto) {
        logger.info("Search tracks: " + dto);
        return trackService.search(dto);
    }

    @POST
    @Path("/tracks")
    @Consumes("application/json")
    public Response create(Track track) {
        logger.info("Create " + track);
        trackService.save(track);
        logger.info("Saved");
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/tracks/{id}")
    @Consumes("application/json")
    public void update(@PathParam("id") String trackId,
                       Track track) {
        logger.info("Update " + track);
        trackService.update(trackId, track);
        logger.info("Updated");
    }

    @DELETE
    @Path("/tracks/delete")
    public void deleteData() {
        logger.info("Delete all data");
        trackService.deleteAll();
        logger.info("Deleted");
    }
}
