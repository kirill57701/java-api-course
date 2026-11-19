package coursach.core;

import java.io.IOException;
import java.util.Map;

public interface ApiSource {
    String name();
    Map<String, String> defaultParams();
    DataRecord fetch(Map<String, String> params) throws IOException, InterruptedException;
}
