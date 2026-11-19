# REST API Data Aggregator

Курсовая работа по объектно-ориентированному программированию.

## Описание

Консольное Java-приложение для последовательного получения данных из нескольких REST API, преобразования их к единой модели и сохранения в файлы JSON или CSV.

## Используемые API

- Open-Meteo - получение текущей температуры.
- JokeAPI - получение случайных шуток.
- Open Library - поиск информации о книгах.

## Архитектура

- `ApiSource` - общий интерфейс источников.
- `DataRecord` - единая модель данных.
- `ApiHttpClient` - выполнение HTTP-запросов.
- `WeatherSource`, `JokeSource`, `BooksSource` - реализации источников.
- `Aggregator` - сбор данных.
- `Storage`, `JsonStorage`, `CsvStorage` - сохранение и чтение файлов.
- `AppFactory` - создание объектов и внедрение зависимостей.
- `Main` - обработка аргументов командной строки.

## Требования

- Java 17+
- Maven 3.9+

## Сборка

```bash
mvn clean package
```

## Запуск

Получение данных в JSON:

```bash
java -jar target/coursework-stage1-1.0.jar collect weather,jokes,books json new
```

Чтение JSON:

```bash
java -jar target/coursework-stage1-1.0.jar read json
```

Сохранение в CSV:

```bash
java -jar target/coursework-stage1-1.0.jar collect weather,jokes,books csv new
```

Чтение CSV:

```bash
java -jar target/coursework-stage1-1.0.jar read csv
```

Дозапись:

```bash
java -jar target/coursework-stage1-1.0.jar collect weather json append
```

Фильтрация по источнику:

```bash
java -jar target/coursework-stage1-1.0.jar read json weather
```

## Особенности

Приложение использует интерфейсы и разделение ответственности между компонентами. Источники API можно расширять новыми реализациями `ApiSource` без изменения логики агрегатора.

На первом этапе опрос выполняется последовательно, в одном потоке.
