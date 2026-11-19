package coursach.cli;

import coursach.config.AppFactory;
import coursach.core.DataRecord;
import coursach.storage.Storage;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            run(args);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void run(String[] args) throws Exception {
        if (args.length == 0) {
            help();
            return;
        }
        switch (args[0]) {
            case "collect" -> {
                if (args.length < 4 || args.length > 5 ||
                        (!args[3].equals("new") && !args[3].equals("append"))) {
                    help();
                    throw new IllegalArgumentException("Неверная команда collect");
                }
                String format = args[2];
                Path path = Path.of(args.length == 5 ? args[4] : "data." + format);
                Storage storage = AppFactory.storage(format, path);
                List<String> names = Arrays.asList(args[1].split(","));
                List<DataRecord> records = AppFactory.aggregator().collect(names);
                storage.save(records, args[3].equals("append"));
                System.out.println("Сохранено записей: " + records.size() + ". Файл: " + path);
            }
            case "read" -> {
                if (args.length < 2 || args.length > 4) {
                    help();
                    throw new IllegalArgumentException("Неверная команда read");
                }
                String format = args[1];
                String source = args.length >= 3 ? args[2] : "all";
                Path path = Path.of(args.length == 4 ? args[3] : "data." + format);
                Storage storage = AppFactory.storage(format, path);
                for (DataRecord record : storage.load()) {
                    if (source.equals("all") || record.source().equals(source)) {
                        System.out.println(record);
                    }
                }
            }
            default -> {
                help();
                throw new IllegalArgumentException("Неизвестная команда: " + args[0]);
            }
        }
    }

    private static void help() {
        System.out.println("Команды:\n" +
                "  collect <weather,jokes,books> <json|csv> <new|append> [файл]\n" +
                "  read <json|csv> [all|weather|jokes|books] [файл]");
    }
}
