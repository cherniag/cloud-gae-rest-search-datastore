package cloud.gae.rest.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.util.logging.Logger;

import cloud.gae.rest.track.datastore.TrackEntity;
import com.googlecode.objectify.ObjectifyService;

/**
 * Author: Gennadii Cherniaiev
 * Date: 8/4/2015
 */
public class StartupListener implements ServletContextListener {
    static final Logger logger = Logger.getLogger(StartupListener.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("contextInitialized: " + servletContextEvent);
        ObjectifyService.register(TrackEntity.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
