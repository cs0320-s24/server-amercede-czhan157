package edu.brown.cs.student;

import edu.brown.cs.student.main.datasource.StateCodes;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStateCodes {
    @Test
    public void testAreWeGettingTheRightStateCode() throws URISyntaxException, IOException, InterruptedException {
        StateCodes theOfficialStateCodes = new StateCodes();
        List<List<String>> theListOfStateCodes = theOfficialStateCodes.getStateCodes();

        //This might need to happen again and again
        String stateQuery = "Virginia";
        String currentStateNum = "";
        for(List<String> i: theListOfStateCodes){
            if(i.contains(stateQuery)){
                currentStateNum = i.get(1);
            }
        }
        assertEquals(51, Integer.valueOf(currentStateNum));
    }
}
