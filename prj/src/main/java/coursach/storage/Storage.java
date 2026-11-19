package coursach.storage;

import coursach.core.DataRecord;
import java.io.IOException;
import java.util.List;

public interface Storage {
    void save(List<DataRecord> records, boolean append) throws IOException;
    List<DataRecord> load() throws IOException;
}
