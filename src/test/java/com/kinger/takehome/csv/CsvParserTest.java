package com.kinger.takehome.csv;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {
    final String columnHeaders = "TeamName,City,State,Division";
    int numColumns = 4;

    @Test
    void HeaderShouldMatch() {
        CsvParser sut = null;
        File testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("mlbtest.csv")).getFile());
        try {
            sut = CsvParser.parse(testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(columnHeaders, sut.getHeader());
        assertEquals(numColumns, sut.getHeaderMap().size());
    }

    @Test
    void EmptyHeaderFieldShouldThrowException() {
        CsvParser sut = null;
        File testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("emptyHeaderFieldTest.csv")).getFile());
        InvalidParameterException ipe = assertThrows(InvalidParameterException.class, () -> CsvParser.parse(testFile));
        assertEquals("Header row cannot have empty columns", ipe.getMessage());
    }

    @Test
    void DuplicateHeaderFieldShouldThrowException() {
        CsvParser sut = null;
        File testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("duplicateHeaderFieldTest.csv")).getFile());
        InvalidParameterException ipe = assertThrows(InvalidParameterException.class, () ->  CsvParser.parse(testFile));
        assertEquals("Header row cannot have duplicate columns", ipe.getMessage());
    }

    @Test
    void HeaderToSortByShouldMatch() {
        CsvParser sut = null;
        File testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("mlbtest.csv")).getFile());
        try {
            sut = CsvParser.parse(testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void CsvFileBodyShouldMatch() {
        CsvParser sut = null;
        File testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("mlbtest.csv")).getFile());
        try {
            sut = CsvParser.parse(testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<Row> body = sut.getBody();
        Row firstRow = body.next();
        
        assertEquals("Arizona Diamondbacks,Phoenix,Arizona,NL West", firstRow.rowLine());
        assertEquals(4, firstRow.line().size());
        assertEquals(30, sut.getNumberOfRows());
    }
}