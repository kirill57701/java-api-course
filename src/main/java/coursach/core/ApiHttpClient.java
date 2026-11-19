package coursach.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiHttpClient {
    private final HttpClient http;
    private final ObjectMapper json;

    public ApiHttpClient(HttpClient http, ObjectMapper json) {
        this.http = http;
        this.json = json;
    }

    public JsonNode get(String url, Map<String, String> params) throws IOException, InterruptedException {
        String query = params.entrySet().stream()
                .map(e -> encode(e.getKey()) + (e.getValue().isEmpty() ? "" : "=" + encode(e.getValue())))
                .collect(Collectors.joining("&"));
        URI uri = URI.create(url + (query.isEmpty() ? "" : "?" + query));
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("User-Agent", "CourseAggregator/1.0")
                .timeout(Duration.ofSeconds(15))
                .GET().build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("HTTP " + response.statusCode() + " от " + uri);
        }
        return json.readTree(response.body());
    }

    private static String encode(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}
