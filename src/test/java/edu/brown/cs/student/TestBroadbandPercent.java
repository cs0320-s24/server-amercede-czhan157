package edu.brown.cs.student;

<<<<<<< HEAD
import edu.brown.cs.student.main.datasource.BroadbandPercent;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

=======
>>>>>>> 552bdb8f85429d4d9abe6576718f84597d52499a
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.brown.cs.student.main.datasource.BroadbandPercent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestBroadbandPercent {
<<<<<<< HEAD
    @Test
    public void testAreWeGettingTheRightBroadbandPercentRow() throws URISyntaxException, IOException, InterruptedException {
        String currentStateNum = "51";
        String currentCountyNum = "199";
        BroadbandPercent theOfficialBroadbandPercents = new BroadbandPercent(currentStateNum, currentCountyNum);
        List<List<String>> theListOfBroadbandPercents = theOfficialBroadbandPercents.getBroadbandPercent();
        List<String> theListWeNeedOfBroadbandPercent = theListOfBroadbandPercents.get(1);
        String[] expected = {"York County, Virginia", "88.5", "51", "199"};
        //This should look like this: [York County, Virginia, 88.5, 51, 199]
        List<String> expectedList = Arrays.asList(expected);
        assertEquals(expectedList, theListWeNeedOfBroadbandPercent);
    }
    @Test
    public void testAreWeGettingTheRightBroadbandPercent() throws URISyntaxException, IOException, InterruptedException {
        String currentStateNum = "51";
        String currentCountyNum = "199";
        BroadbandPercent theOfficialBroadbandPercents = new BroadbandPercent(currentStateNum, currentCountyNum);
        List<List<String>> theListOfBroadbandPercents = theOfficialBroadbandPercents.getBroadbandPercent();
        List<String> theListWeNeedOfBroadbandPercent = theListOfBroadbandPercents.get(1);
        assertEquals(88.5, theListWeNeedOfBroadbandPercent.get(1));
    }

=======
  @Test
  public void testAreWeGettingTheRightBroadbandPercent()
      throws URISyntaxException, IOException, InterruptedException {
    String currentStateNum = "51";
    String currentCountyNum = "199";
    BroadbandPercent theOfficialBroadbandPercents =
        new BroadbandPercent(currentStateNum, currentCountyNum);
    List<List<String>> theListOfBroadbandPercents =
        theOfficialBroadbandPercents.getBroadbandPercent();
    List<String> theListWeNeedOfBroadbandPercent = theListOfBroadbandPercents.get(1);
    assertEquals(88.5, Double.valueOf(theListWeNeedOfBroadbandPercent.get(1)));
  }
>>>>>>> 552bdb8f85429d4d9abe6576718f84597d52499a
}
