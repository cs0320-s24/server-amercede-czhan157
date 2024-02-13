package edu.brown.cs.student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.testng.Assert.assertThrows;

public class SearcherTests {

    @Test
    void emptyObjectToSearchForWillThrowNullPointerException(){
        Searcher searcher = new Searcher();
        String fileName = "census/dol_ri_earnings_disparity";
        assertThrows(NullPointerException.class,
                () -> {
                    searcher.foundRows(fileName, null, 4,
                            "item", true, true);
                });
    }


    @Test
    void emptyNameWillNotThrowException(){
        Searcher searcher = new Searcher();
        String fileName = "census/dol_ri_earnings_disparity";
        assertDoesNotThrow(
                () -> {
                    searcher.foundRows(fileName, "hello", 4,
                            null, true, true);
                });
    }

}
