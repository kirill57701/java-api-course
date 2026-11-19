package ru.course.aggregator.sources;

import ru.course.aggregator.core.ApiHttpClient;
import ru.course.aggregator.core.ApiSource;
import ru.course.aggregator.core.DataRecord;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

public class BooksSource implements ApiSource {
    private final ApiHttpClient client;

    public BooksSource(ApiHttpClient client) {
        this.client = client;
    }

    public String name() { return "books"; }

    public Map<String, String> defaultParams() {
        return Map.of("title", "Java", "limit", "1");
    }

    public DataRecord fetch(Map<String, String> params) throws IOException, InterruptedException {
        JsonNode json = client.get("https://openlibrary.org/search.json", params);
        JsonNode book = json.path("docs").path(0);
        if (book.isMissingNode()) throw new IOException("Open Library не нашла книги");
        return new DataRecord(name(), params.toString(), book.path("title").asText(),
                book.path("author_name").path(0).asText("Автор неизвестен"), Instant.now().toString());
    }
}
