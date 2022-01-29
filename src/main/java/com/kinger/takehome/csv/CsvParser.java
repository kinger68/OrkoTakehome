package com.kinger.takehome.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser {
    private final Stream<String> csvFileStream;
    private final ColumnHeader headers;

    // Using a record removes boilerplate constructor and field initialization
    private record ColumnHeader(Map<String, Integer> header, String headerLine) {
    }

    public static CsvParser parse(final File file) throws IOException {
        return new CsvParser(file);
    }

    public CsvParser(File csvFile) throws IOException {
        this.csvFileStream = Files.lines(csvFile.toPath());
        this.headers = createHeader(Files.lines(csvFile.toPath()).findFirst().get());
        Files.lines(csvFile.toPath()).forEach(System.out::println);
    }

    public String getHeader() {
        return this.headers.headerLine;
    }

    public Map<String, Integer> getHeaderMap() {
        return this.headers.header;
    }

    public List<String> parseLine(String csvDelimitedString) {
        return Stream.of(csvDelimitedString.split(",", -1)).collect(Collectors.toList());
    }

    public boolean hasEmptyElements(List<String> tokens) {
        for (String s : tokens) {
            if (s.length() == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDuplicateElements(List<String> tokens) {
        Set<String> duplicates = new HashSet<>();
        for(String s : tokens) {
            if(!duplicates.add(s)) return true;
        }
        return false;
    }

    private ColumnHeader createHeader(String headerLine) {
        List<String> headerList = parseLine(headerLine);
        if(hasEmptyElements(headerList)) throw new InvalidParameterException("Header row cannot have empty columns");
        if(hasDuplicateElements(headerList)) throw new InvalidParameterException("Header row cannot have duplicate columns");

        Map<String, Integer> header = new HashMap<>();
        for (int i = 0; i < headerList.size(); i++) {
            header.put(headerList.get(i), i);
        }
        return new ColumnHeader(header, headerLine);
    }

}
