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
    private final Row headers;
    private final List<Row> bodyElements;

    public static CsvParser parse(final File file) throws IOException {
        return new CsvParser(file);
    }

    public CsvParser(File csvFile) throws IOException {
        this.csvFileStream = Files.lines(csvFile.toPath());
        this.headers = createHeader(Files.lines(csvFile.toPath()).findFirst().get());
        this.bodyElements = createBodyElements(Files.lines(csvFile.toPath())
                .skip(1)
                .toList());
    }

    // Create a list of records containing each row in the CSV that is not the line
    private List<Row> createBodyElements(List<String> csvBody) {
        List<Row> bodyElements = new ArrayList<>();
        for (String bodyElement : csvBody) {
            List<String> parsedRow = parseLine(bodyElement);
            Map<Integer, String> rowElements = new HashMap<>();
            for (int i = 0; i < parsedRow.size(); i++) {
                rowElements.put(i, parsedRow.get(i));
            }
            bodyElements.add(new Row(rowElements, bodyElement));
        }
        return bodyElements;
    }

    public String getHeader() {
        return this.headers.rowLine();
    }

    public Map<Integer, String> getHeaderMap() {
        return this.headers.line();
    }

    public ListIterator<Row> getBody() {
        return this.bodyElements.listIterator();
    }

    public int getNumberOfRows() {
        return this.bodyElements.size();
    }

    public ListIterator<Row> sort(int whichColumn) {
        // What if the column number is > size of the number of columns
        if(whichColumn >= getHeaderMap().size()) throw new InvalidParameterException("Sort column number exceeds number of columns in file");
        return bodyElements.stream()
                .sorted(Comparator.comparing(i -> i.line().get(whichColumn), Comparator.reverseOrder()))
                .toList().listIterator();
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

    private Row createHeader(String headerLine) {
        List<String> headerList = parseLine(headerLine);
        if(hasEmptyElements(headerList)) throw new InvalidParameterException("Header row cannot have empty columns");
        if(hasDuplicateElements(headerList)) throw new InvalidParameterException("Header row cannot have duplicate columns");

        Map<Integer, String> header = new HashMap<>();
        for (int i = 0; i < headerList.size(); i++) {
            header.put(i, headerList.get(i));
        }
        return new Row(header, headerLine);
    }

}
