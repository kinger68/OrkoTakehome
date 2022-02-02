package com.kinger.takehome;

import com.kinger.takehome.csv.CsvParser;
import com.kinger.takehome.csv.Row;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static java.lang.System.exit;

public class App {
    public static void main(String[] args) {
        CsvParser parsedCsvFile = null;
        if(args.length < 2) {
            System.out.println("Must include the path to the file to be parsed and which column to parse");
            exit(1);
        }

        try {
            parsedCsvFile = CsvParser.parse(new File(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }

        // Subtract 1 from the sort column so user can enter a number between 1 and the number
        // of columns they want.
        int sortColumn = Integer.parseInt(args[1]) ;

        Iterator<Row> sortedRows = parsedCsvFile.sort(sortColumn - 1);
        System.out.println(parsedCsvFile.getHeader());
        sortedRows.forEachRemaining(row -> System.out.println(row.rowLine()));
    }
}
