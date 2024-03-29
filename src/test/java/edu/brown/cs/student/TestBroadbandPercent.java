package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.brown.cs.student.main.datasource.BroadbandPercent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestBroadbandPercent {
  @Test
  public void testAreWeGettingTheRightBroadbandPercentRow()
      throws URISyntaxException, IOException, InterruptedException {
    String currentStateNum = "51";
    String currentCountyNum = "199";
    BroadbandPercent theOfficialBroadbandPercents =
        new BroadbandPercent(currentStateNum, currentCountyNum);
    List<List<String>> theListOfBroadbandPercents =
        theOfficialBroadbandPercents.getBroadbandPercent();
    List<String> theListWeNeedOfBroadbandPercent = theListOfBroadbandPercents.get(1);
    String[] expected = {"York County, Virginia", "88.5", "51", "199"};
    // This should look like this: [York County, Virginia, 88.5, 51, 199]
    List<String> expectedList = Arrays.asList(expected);
    assertEquals(expectedList, theListWeNeedOfBroadbandPercent);
  }

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
    assertEquals("88.5", theListWeNeedOfBroadbandPercent.get(1));
  }

  @Test
  public void testThrowsIOExceptionForNullInStateNum()
      throws URISyntaxException, IOException, InterruptedException {

    assertThrows(
        IOException.class,
        () -> {
          // Should give error message: BroadbandPercent: string wasn't valid JSON.
          BroadbandPercent theOfficialBroadbandPercents = new BroadbandPercent(null, "199");
        });
  }

  @Test
  public void testThrowsIOExceptionForNullInCountyNum()
      throws URISyntaxException, IOException, InterruptedException {

    assertThrows(
        IOException.class,
        () -> {
          // Should give error message: BroadbandPercent: string wasn't valid JSON.
          BroadbandPercent theOfficialBroadbandPercents = new BroadbandPercent("51", null);
        });
  }

  // Example query to test:
  // http://localhost:3232/census?statename=Alabama&countyname=Morgan%20County
  // Should display: {"broadband":["Morgan County, Alabama","75.2","01","103"],"type":"success"}
}
