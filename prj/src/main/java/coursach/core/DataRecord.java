package coursach.core;

public record DataRecord(
        String source,
        String query,
        String title,
        String value,
        String collectedAt
) { }
