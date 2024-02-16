package edu.brown.cs.student;

import edu.brown.cs.student.main.datasource.BroadbandPercent;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBroadbandPercent {
    @Test
    public void testAreWeGettingTheRightBroadbandPercent() throws URISyntaxException, IOException, InterruptedException {
        String currentStateNum = "51";
        String currentCountyNum = "199";
        BroadbandPercent theOfficialBroadbandPercents = new BroadbandPercent(currentStateNum, currentCountyNum);
        List<List<String>> theListOfBroadbandPercents = theOfficialBroadbandPercents.getBroadbandPercent();
        List<String> theListWeNeedOfBroadbandPercent = theListOfBroadbandPercents.get(1);
        assertEquals(88.5, Double.valueOf(theListWeNeedOfBroadbandPercent.get(1)));
    }
}
