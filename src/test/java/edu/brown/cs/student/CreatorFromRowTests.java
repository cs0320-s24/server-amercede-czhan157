package edu.brown.cs.student;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreatorFromRowTests {

    @Test
    void whenCreatorFromRowListOfStringsIsNullThrowFactoryFailureException(){
        CreatorFromRow<Integer> c = new BrokenReturnT();
        List<String> myList = new ArrayList<>();
        assertThrows(FactoryFailureException.class,
                () -> {
                    c.create(myList);
                });
    }

}
