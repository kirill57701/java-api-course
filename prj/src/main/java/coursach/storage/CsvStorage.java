package ru.course.aggregator.storage;

import ru.course.aggregator.core.DataRecord;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvStorage implements Storage {
    private final Path path;
    private final CsvMapper mapper;
    private final CsvSchema schema = CsvSchema.builder()
            .addColumn("source").addColumn("query").addColumn("title")
            .addColumn("value").addColumn("collectedAt")
            .setUseHeader(true).build();

    public CsvStorage(Path path, CsvMapper mapper) {
        this.path = path;
        this.mapper = mapper;
    }

    public List<DataRecord> load() throws IOException {
        if (!Files.exists(path)) return new ArrayList<>();
        return mapper.readerFor(DataRecord.class).with(schema)
                .<DataRecord>readValues(path.toFile()).readAll();
    }

    public void save(List<DataRecord> records, boolean append) throws IOException {
        List<DataRecord> all = append ? load() : new ArrayList<>();
        all.addAll(records);
        mapper.writer(schema).writeValue(path.toFile(), all);
    }
}
