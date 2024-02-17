package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.brown.cs.student.main.csv.CSVParser;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestCSVUnit {
  /** parser test for my default formatter */
  @Test
  public void DefaultHeader() {

    StringReader stringReader =
        new StringReader(
            "SongName,Artist,ID\n"
                + "All Too Well,Taylor Swift,20\n"
                + "Better Than Revenge,Taylor Swift,25\n"
                + "Waiting Room,Phoebe Bridgers,30\n");

    CSVParser parser = new CSVParser(stringReader, true);

    try {
      parser.Parse();
    } catch (Exception e) {

    }

    String[] colNames = parser.getColumnNames();
    List<List<String>> records = parser.getRawResults();

    assertTrue(parser.getColumnCount() == 3);
    assertTrue(parser.getNumRows() == 3);

    assertTrue(colNames[0].equals("SongName"));
    assertTrue(colNames[1].equals("Artist"));
    assertTrue(colNames[2].equals("ID"));

    assertTrue(records.get(1).get(0).equals("All Too Well"));
    assertTrue(records.get(1).get(1).equals("Taylor Swift"));
    assertTrue(records.get(1).get(2).equals("20"));

    assertTrue(records.get(2).get(0).equals("Better Than Revenge"));
    assertTrue(records.get(2).get(1).equals("Taylor Swift"));
    assertTrue(records.get(2).get(2).equals("25"));

    assertTrue(records.get(3).get(0).equals("Waiting Room"));
    assertTrue(records.get(3).get(1).equals("Phoebe Bridgers"));
    assertTrue(records.get(3).get(2).equals("30"));
  }

  /** parser test for no headers */
  @Test
  public void DefaultNoHeader() {

    StringReader stringReader =
        new StringReader(
            "All Too Well,Taylor Swift,20\n"
                + "Better Than Revenge,Taylor Swift,25\n"
                + "Waiting Room,Phoebe Bridgers,30\n");

    CSVParser parser = new CSVParser(stringReader, false);

    try {
      parser.Parse();
    } catch (Exception e) {

    }

    List<List<String>> results = parser.getRawResults();
    System.out.println(parser.getColumnCount());
    assertTrue(parser.getColumnCount() == 3);
    assertTrue(parser.getNumRows() == 3);

    assertTrue(results.get(0).get(0).equals("All Too Well"));
    assertTrue(results.get(0).get(1).equals("Taylor Swift"));
    assertTrue(results.get(0).get(2).equals("20"));

    assertTrue(results.get(1).get(0).equals("Better Than Revenge"));
    assertTrue(results.get(1).get(1).equals("Taylor Swift"));
    assertTrue(results.get(1).get(2).equals("25"));

    assertTrue(results.get(2).get(0).equals("Waiting Room"));
    assertTrue(results.get(2).get(1).equals("Phoebe Bridgers"));
    assertTrue(results.get(2).get(2).equals("30"));
  }
}
