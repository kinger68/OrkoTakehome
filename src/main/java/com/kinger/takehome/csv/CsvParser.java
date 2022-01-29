package com.kinger.takehome.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CsvParser {
    Stream<String> csvFileStream;
    private final ColumnHeader headers;

    private static final class ColumnHeader {
        Map<String, Integer> header;
        String headerLine;

        ColumnHeader(String headerLine) {
            this.headerLine = headerLine;
            createHeaderMap(headerLine);
        }

        private void createHeaderMap(String headerLine) {
            System.out.println(headerLine);
            header = new HashMap<>();
        }

    }

    public CsvParser(File csvFile) throws IOException {
        csvFileStream = Files.lines(csvFile.toPath());
        headers = new ColumnHeader(csvFileStream.findFirst().get());
        // csvFileStream.forEach(System.out::println);
    }

    public String getHeader() {
        return this.headers.headerLine;
    }

    public Map<String, Integer> getHeaderMap() {
        return this.headers.header;
    }

    public static CsvParser parse(final File file) throws IOException {
        return new CsvParser(file);
    }

}
