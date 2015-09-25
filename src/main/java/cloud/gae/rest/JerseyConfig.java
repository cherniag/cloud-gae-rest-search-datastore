package cloud.gae.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        // Enable Spring DI
        register(RequestContextFilter.class);
        //register(JacksonObjectMapperConfig.class);
        register(JacksonFeature.class);

        register(TrackController.class);
    }
}
