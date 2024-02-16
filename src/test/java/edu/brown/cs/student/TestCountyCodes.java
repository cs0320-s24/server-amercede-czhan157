package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.brown.cs.student.main.datasource.CountyCodes;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestCountyCodes {

  @Test
  public void testAreWeGettingTheRightCountyCodeList()
      throws URISyntaxException, IOException, InterruptedException {
    String currentStateNum = "51";
    String stateQuery = "Virginia";
    CountyCodes theOfficialCountyCodes = new CountyCodes(currentStateNum);
    List<List<String>> theListOfCountyCodes = theOfficialCountyCodes.getCountyCodes();
    String countyQuery = "York County, " + stateQuery;
    List<String> theCountyCodeListWeNeed = null;
    for (List<String> i : theListOfCountyCodes) {
      if (i.contains(countyQuery)) {
        theCountyCodeListWeNeed = i;
      }
    }
    String[] expected = {"York County, Virginia", "51", "199"};
    // This should look like this: [York County, Virginia, 51, 199]
    List<String> expectedList = Arrays.asList(expected);
    assertEquals(expectedList, theCountyCodeListWeNeed);
  }

  @Test
  public void testAreWeGettingTheRightCountyCode()
      throws URISyntaxException, IOException, InterruptedException {
    String currentStateNum = "51";
    String stateQuery = "Virginia";
    CountyCodes theOfficialCountyCodes = new CountyCodes(currentStateNum);
    List<List<String>> theListOfCountyCodes = theOfficialCountyCodes.getCountyCodes();
    String countyQuery = "York County, " + stateQuery;
    String currentCountyNum = "";
    for (List<String> i : theListOfCountyCodes) {
      if (i.contains(countyQuery)) {
        currentCountyNum = i.get(2);
      }
    }
    assertEquals(199, Integer.valueOf(currentCountyNum));
  }

  @Test
  public void testThrowsIOExceptionForNull()
          throws URISyntaxException, IOException, InterruptedException {

    assertThrows(IOException.class,
            () -> {

              CountyCodes theOfficialCountyCodes = new CountyCodes(null);
            });
  }
  @Test
  public void testThrowsIOExceptionForString()
          throws URISyntaxException, IOException, InterruptedException {

    assertThrows(IOException.class,
            () -> {

              CountyCodes theOfficialCountyCodes = new CountyCodes("hello");
            });
  }
}
