package com.kinger.takehome.csv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {
    String headerString = "TeamName,City,State,Division";
    CsvParser sut;

    @BeforeEach
    void setUp() throws IOException {
        File testFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("test.csv")).getFile());
        sut = CsvParser.parse(testFile);
    }

    @Test
    void HeaderShouldMatch() {
        assertEquals(headerString, sut.getHeader());
    }

    @Test
    void HeaderShouldHave3Columns() {
        assertEquals(4, sut.getHeaderMap().size());
    }
}