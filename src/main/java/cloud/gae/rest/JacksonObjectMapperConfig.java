package cloud.gae.rest;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Author: Gennadii Cherniaiev Date: 8/25/2015
 */
@Provider
public class JacksonObjectMapperConfig
    implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper OBJECT_MAPPER
        = new ObjectMapper()
        .disable(MapperFeature.AUTO_DETECT_CREATORS)
        .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        return OBJECT_MAPPER;
    }
}
