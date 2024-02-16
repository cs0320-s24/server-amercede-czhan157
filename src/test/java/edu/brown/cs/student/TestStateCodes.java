package edu.brown.cs.student;

import edu.brown.cs.student.main.datasource.StateCodes;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStateCodes {
    @Test
    public void testAreWeGettingTheRightStateCodeList() throws URISyntaxException, IOException, InterruptedException {
        StateCodes theOfficialStateCodes = new StateCodes();
        List<List<String>> theListOfStateCodes = theOfficialStateCodes.getStateCodes();

        //This might need to happen again and again
        String stateQuery = "Virginia";
        List<String> theStateCodeListWeNeed = null;
        for(List<String> i: theListOfStateCodes){
            if(i.contains(stateQuery)){
                theStateCodeListWeNeed = i;
            }
        }
        String[] expected = {"Virginia", "51"};
        //This should look like this: [Virginia, 51]
        List<String> expectedList = Arrays.asList(expected);
        assertEquals(expectedList, theStateCodeListWeNeed);
    }
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
