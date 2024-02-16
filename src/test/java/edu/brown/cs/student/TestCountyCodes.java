package edu.brown.cs.student;

import edu.brown.cs.student.main.datasource.CountyCodes;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCountyCodes {
    @Test
    public void testAreWeGettingTheRightCountyCode() throws URISyntaxException, IOException, InterruptedException {
        String currentStateNum = "51";
        String stateQuery = "Virginia";
        CountyCodes theOfficialCountyCodes = new CountyCodes(currentStateNum);
        List<List<String>> theListOfCountyCodes = theOfficialCountyCodes.getCountyCodes();
        String countyQuery = "York County, " + stateQuery;
        String currentCountyNum = "";
        for(List<String> i: theListOfCountyCodes){
            if(i.contains(countyQuery)){
                currentCountyNum = i.get(2);
            }
        }
        assertEquals(199, Integer.valueOf(currentCountyNum));
    }
}
