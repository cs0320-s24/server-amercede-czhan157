package edu.brown.cs.student;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTests {

    @Test
    void whenReturnParserIsGivenNullArgumentNullThrowNullPointerException() throws FileNotFoundException {

        String pathDirectory = ("/Users/alexandramercedes/csv-amercede/data/census/dol_ri_earnings_disparity.csv");
        File testingFile = new File(pathDirectory);
        Reader dirReader = new FileReader(pathDirectory);
        CreatorFromRow createdRow = new ReturnT();
        Parser workingParser = new Parser<>(dirReader, createdRow);

        assertThrows(NullPointerException.class,
                () -> {
                    workingParser.returnParser(null);
                });
    }
}
