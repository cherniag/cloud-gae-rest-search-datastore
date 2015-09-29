package cloud.gae.rest.track.search;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import java.util.HashMap;
import java.util.Map;
/**
 * Author: Gennadii Cherniaiev Date: 9/29/2015
 */
public class TrackSearchDto {

    @QueryParam("artist")
    private String artist;

    @QueryParam("title")
    private String title;

    @QueryParam("isrc")
    private String isrc;

    @QueryParam("page")
    @DefaultValue("0")
    private Integer page;

    @QueryParam("size")
    @DefaultValue("10")
    private Integer size;

    public Integer getOffset() {
        return page * size;
    }

    public Integer getLimit() {
        return size;
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("artist", artist);
        params.put("title", title);
        params.put("isrc", isrc);
        return params;
    }

    @Override
    public String toString() {
        return "TrackSearchDto{" +
               "artist='" + artist + '\'' +
               ", title='" + title + '\'' +
               ", isrc='" + isrc + '\'' +
               ", page=" + page +
               ", size=" + size +
               '}';
    }
}
