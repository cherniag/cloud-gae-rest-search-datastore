package cloud.gae.rest.config;

import cloud.gae.rest.track.web.TrackController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        // Enable Spring DI
        register(RequestContextFilter.class);
        // JSON converter
        register(JerseyMapperProvider.class);

        // Application endpoints
        register(TrackController.class);
    }
}
