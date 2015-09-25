package cloud.gae.rest;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.List;

/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Path("/api")
public class TrackController {

    @Resource
    private TrackService trackService;


    @GET
    @Path("/tracks")
    @Produces("application/json")
    public List<Track> getAll() {
         return null;
    }

    @POST
    @Path("/tracks")
    @Consumes("application/json")
    public void create(Track track) {

    }
}
