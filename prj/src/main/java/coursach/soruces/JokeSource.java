package coursach.sources;

import coursach.core.ApiHttpClient;
import coursach.core.ApiSource;
import coursach.core.DataRecord;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

public class JokeSource implements ApiSource {
    private final ApiHttpClient client;

    public JokeSource(ApiHttpClient client) {
        this.client = client;
    }

    public String name() { return "jokes"; }

    public Map<String, String> defaultParams() {
        return Map.of("type", "single", "lang", "en", "safe-mode", "");
    }

    public DataRecord fetch(Map<String, String> params) throws IOException, InterruptedException {
        JsonNode json = client.get("https://v2.jokeapi.dev/joke/Programming", params);
        if (json.path("error").asBoolean()) {
            throw new IOException("JokeAPI: " + json.path("message").asText());
        }
        String joke = json.path("type").asText().equals("single")
                ? json.path("joke").asText()
                : json.path("setup").asText() + " " + json.path("delivery").asText();
        if (joke.isBlank()) throw new IOException("JokeAPI вернул пустой ответ");
        return new DataRecord(name(), params.toString(), "Шутка о программировании",
                joke, Instant.now().toString());
    }
}
