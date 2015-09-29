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
import com.google.appengine.api.search.GetRequest;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
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
        Document document = document(trackKey,
                                     text("title", track.title),
                                     text("artist", track.artist));
        try {
            PutResponse putResponse = index().put(document);
            logger.info("Put response: " + putResponse);
        } catch (PutException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }

    public List<String> search(String artist, String title, Integer page, Integer size) {
        long now = System.currentTimeMillis();
        String queryString = createQuery(artist, title);
        QueryOptions queryOptions = QueryOptions.newBuilder().setReturningIdsOnly(true).setOffset(page * size).setLimit(size).build();
        Query query = Query.newBuilder().setOptions(queryOptions).build(queryString);
        logger.info("QueryString [" + queryString);

        Results<ScoredDocument> documents = index().search(query);
        logger.info("Search operational result, elapsed " + (System.currentTimeMillis() - now) + " ms");

        List<String> keys = new ArrayList<>();
        for (ScoredDocument scoredDocument : documents) {
            keys.add(scoredDocument.getId());
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
                queryString.append(" AND ");
            }
            queryString.append("title=\"").append(title).append("\"");
        }
        return queryString.toString();
    }

    private Document document(String trackKey, Field... fields) {
        Document.Builder builder = Document.newBuilder();
        builder.setId(trackKey);
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
        return Field.newBuilder().setName(name).setText(tokenize(value)).build();
    }

    private String tokenize(String value) {
        String normalized = value.toLowerCase();
        if (normalized.length() < 3) {
            return normalized;
        }
        StringBuilder result = new StringBuilder();
        Set<String> strings = new HashSet<>();

        for (int start = 0; start < normalized.length(); start++) {
            for (int end = start + 3; end < normalized.length() + 1; end++) {
                String token = normalized.substring(start, end).trim();
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

        logger.log(Level.INFO, "Tokenize [" + value + "], initial length " + value.length() + ", result length " + result.length());
        return result.toString();
    }

    public void deleteAll() {
        try {
            // looping because getRange by default returns up to 100 documents at a time
            while (true) {
                List<String> docIds = new ArrayList<>();
                // Return a set of doc_ids.
                GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();
                GetResponse<Document> response = index().getRange(request);
                if (response.getResults().isEmpty()) {
                    break;
                }
                for (Document doc : response) {
                    docIds.add(doc.getId());
                }
                index().delete(docIds);
            }
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "Failed to delete documents", e);
        }
    }
}
