package coursach.config;

import coursach.core.Aggregator;
import coursach.core.ApiHttpClient;
import coursach.sources.BooksSource;
import coursach.sources.JokeSource;
import coursach.sources.WeatherSource;
import coursach.storage.CsvStorage;
import coursach.storage.JsonStorage;
import coursach.storage.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import java.net.http.HttpClient;
import java.nio.file.Path;
import java.util.List;

public class AppFactory {
    public static Aggregator aggregator() {
        ApiHttpClient client = new ApiHttpClient(HttpClient.newHttpClient(), new ObjectMapper());
        return new Aggregator(List.of(
                new WeatherSource(client), new JokeSource(client), new BooksSource(client)));
    }

    public static Storage storage(String format, Path path) {
        return switch (format) {
            case "json" -> new JsonStorage(path, new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT));
            case "csv" -> new CsvStorage(path, new CsvMapper());
            default -> throw new IllegalArgumentException("Формат должен быть json или csv");
        };
    }
}
