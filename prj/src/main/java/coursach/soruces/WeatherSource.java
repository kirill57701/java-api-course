package ru.course.aggregator.sources;

import ru.course.aggregator.core.ApiHttpClient;
import ru.course.aggregator.core.ApiSource;
import ru.course.aggregator.core.DataRecord;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

public class WeatherSource implements ApiSource {
    private final ApiHttpClient client;

    public WeatherSource(ApiHttpClient client) {
        this.client = client;
    }

    public String name() { return "weather"; }

    public Map<String, String> defaultParams() {
        return Map.of("latitude", "52.52", "longitude", "13.41", "current", "temperature_2m");
    }

    public DataRecord fetch(Map<String, String> params) throws IOException, InterruptedException {
        JsonNode json = client.get("https://api.open-meteo.com/v1/forecast", params);
        JsonNode temperature = json.path("current").path("temperature_2m");
        if (temperature.isMissingNode()) throw new IOException("Нет температуры в ответе Open-Meteo");
        return new DataRecord(name(), params.toString(),
                "Температура: " + params.get("latitude") + ", " + params.get("longitude"),
                temperature.asText() + " °C", Instant.now().toString());
    }
}
