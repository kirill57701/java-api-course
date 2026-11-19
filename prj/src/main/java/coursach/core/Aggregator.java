package coursach.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aggregator {
    private final Map<String, ApiSource> sources = new HashMap<>();

    public Aggregator(List<ApiSource> sources) {
        for (ApiSource source : sources) this.sources.put(source.name(), source);
    }

    public List<DataRecord> collect(List<String> names) throws IOException, InterruptedException {
        List<DataRecord> records = new ArrayList<>();
        for (String name : names) {
            ApiSource source = sources.get(name);
            if (source == null) throw new IllegalArgumentException("Неизвестный API: " + name);
            records.add(source.fetch(source.defaultParams()));
        }
        return records;
    }

    public DataRecord fetch(String name, Map<String, String> params) throws IOException, InterruptedException {
        ApiSource source = sources.get(name);
        if (source == null) throw new IllegalArgumentException("Неизвестный API: " + name);
        return source.fetch(params);
    }
}
