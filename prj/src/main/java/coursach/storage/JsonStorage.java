package coursach.storage;

import coursach.core.DataRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage implements Storage {
    private final Path path;
    private final ObjectMapper mapper;

    public JsonStorage(Path path, ObjectMapper mapper) {
        this.path = path;
        this.mapper = mapper;
    }

    public List<DataRecord> load() throws IOException {
        if (!Files.exists(path)) return new ArrayList<>();
        return mapper.readValue(path.toFile(), new TypeReference<List<DataRecord>>() {});
    }

    public void save(List<DataRecord> records, boolean append) throws IOException {
        List<DataRecord> all = append ? load() : new ArrayList<>();
        all.addAll(records);
        mapper.writeValue(path.toFile(), all);
    }
}
