package edu.brown.cs.student.main.parser;

import edu.brown.cs.student.main.csv.CSVParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

//I'm not sure which class it's importing From
//import csv.CSVParser;

public final class Main {
    /**
     * The main method of my CSV library.
     *
     * @param args command line arguments to be parsed example usage: ./run -fileName
     *     ./testing_csvs/test1.csv -searchFor "Tim" -header
     */
    public static void main(String[] args) {
        // set up argument parser class
        ArgParse parseArgs = new ArgParse(args);

        // make sure arguments are valid before attempting to parse
        if (!(parseArgs.filePath != null
                && parseArgs.filePath.length() >= 1
                && parseArgs.searchFor != null
                && parseArgs.searchFor.length() > 0)) {
            System.err.println(
                    "usage: -fileName path_to_file -searchFor text_to_search [-columnIdx column_index | -columnName column_name]"
                            + " [-withHeader]");
            System.exit(1);
        } else {
            parseArgs.printExecution();
        }

        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(parseArgs.filePath)); // create reader for the file

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(1);
        }

        // use the default row formatter in this case
        DefaultFormatter defaultFormatter = new DefaultFormatter();

        CSVParser<List<String>> parser =
                new CSVParser<List<String>>(reader, defaultFormatter, ",", parseArgs.withHeader);

        try {
            parser.Parse();
        } catch (Exception e) {
            System.err.println("ERROR:" + e.getMessage());
            System.exit(1);
        }

        // instantiate the search results array
        List<List<String>> searchResults = null;

        // instantiate the search utility program
        CSVSearchUtility searcher = new CSVSearchUtility(parser.getRawResults());

        // parse the arguments to see if user provided more specific information about columns to search
        // within
        Integer searchColumnIndex = parseArgs.columnIdx;
        String searchColumnName = parseArgs.columnName;
        String searchTerm = parseArgs.searchFor;
        if (searchColumnIndex >= 0) { // search within a specific column index
            searchResults = searcher.findWord(searchTerm, searchColumnIndex);

        } else if (searchColumnName != null) { // search within a specific column name
            searchResults = searcher.findWord(searchTerm, searchColumnName);

        } else { // no such column is specified
            searchResults = searcher.searchFor(searchTerm);
        }

        // this will print the search results to the terminal
        if ((searchResults.size() > 0) && (searchResults != null)) {
            for (List<String> result : searchResults) {
                System.out.println(defaultFormatter.create(result));
            }
        } else {
            System.out.println("No such records found.");
        }
    }
}