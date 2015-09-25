package cloud.gae.rest.track.search;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import cloud.gae.rest.track.service.Track;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;

import org.springframework.stereotype.Service;
/**
 * Author: Gennadii Cherniaiev Date: 9/25/2015
 */
@Service
public class TrackSearchService {

    private static final String INDEX_NAME = "Track";
    private final Logger logger = Logger.getLogger(getClass().getName());

    public void add(Track track, String trackKey) {
        Document document = document(text("key", trackKey),
                                     index("title", track.title),
                                     index("artist", track.artist));
        try {
            PutResponse putResponse = index().put(document);
            logger.info("Put response: " + putResponse);
        } catch (PutException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public List<String> search(String artist, String title) {
        String queryString = createQuery(artist, title);
        Query query = Query.newBuilder().build(queryString);
        logger.info("queryString [" + queryString + "]");

        Results<ScoredDocument> result = index().search(query);
        logger.info("Result: " + result.getOperationResult());

        List<String> keys = new ArrayList<>();
        for (ScoredDocument scoredDocument : result) {
            keys.add(scoredDocument.getOnlyField("key").getText());
        }
        return keys;
    }

    private String createQuery(String artist, String title) {
        StringBuilder queryString = new StringBuilder();
        if (artist != null && !artist.isEmpty()) {
            queryString.append("artist=\"").append(artist).append("\"");
        }
        if (title != null && !title.isEmpty()) {
            if (queryString.length() != 0) {
                queryString.append(" OR ");
            }
            queryString.append("title=\"").append(title).append("\"");
        }
        return queryString.toString();
    }

    private Document document(Field... fields) {
        Document.Builder builder = Document.newBuilder();
        for (Field field : fields) {
            builder.addField(field);
        }
        return builder.build();
    }

    private Index index() {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(INDEX_NAME).build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }

    private Field text(String name, String value) {
        return Field.newBuilder().setName(name).setText(value).build();
    }

    private Field index(String name, String value) {
        return Field.newBuilder().setName(name).setText(tokenize(value)).build();
    }

    private String tokenize(String value) {
        logger.info("tokenize " + value);
        StringBuilder result = new StringBuilder();
        Set<String> strings = new HashSet<>();

        for (int start = 0; start < value.length(); start++) {
            for (int end = start + 1; end < value.length() + 1; end++) {
                String token = value.substring(start, end).trim();
                if (token.isEmpty()) {
                    continue;
                }
                if (strings.add(token)) {
                    if (result.length() > 0) {
                        result.append(",");
                    }
                    result.append(token);
                }
            }
        }

        logger.info("result: " + result);
        return result.toString();
    }

}
