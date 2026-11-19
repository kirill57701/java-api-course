package coursach.core;

import java.io.IOException;
import java.util.Map;

public interface ApiSource {
    String n();
    Map<String, String> defpar();
    DataRecord fetch(Map<String, String> p) throws IOException, InterruptedException;
}
