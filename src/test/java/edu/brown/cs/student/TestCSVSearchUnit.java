package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.brown.cs.student.main.csv.CSVParser;
import edu.brown.cs.student.main.csv.CSVSearchUtility;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestCSVSearchUnit {

  List<String> row1 = new ArrayList<>(Arrays.asList("Cat", "Mammal"));
  List<String> row2 = new ArrayList<>(Arrays.asList("Alligator", "Reptile"));
  List<String> row3 = new ArrayList<>(Arrays.asList("Frog", "Amphibian"));
  List<String> row4 = new ArrayList<>(Arrays.asList("Dog", "Mammal"));
  List<List<String>> rawRecords = new ArrayList(Arrays.asList(row1, row2, row3, row4));
  CSVSearchUtility testSearcher = new CSVSearchUtility(rawRecords);

  /** Looking within a specific index a */
  @Test
  public void withIndex1() {

    String searchWord = "Cat";
    List<List<String>> expected = new ArrayList<>();
    expected.add(row1);
    Integer columnIndex = 0;
    List<List<String>> actual = testSearcher.findWord(searchWord, columnIndex);

    assertEquals(expected, actual);
  }

  /** Looking within a specific index */
  @Test
  public void withIndex2() {
    String searchWord = "Alligator";
    List<List<String>> expected = new ArrayList<>();
    expected.add(row2);
    Integer columnIndex = 0;
    List<List<String>> actual = testSearcher.findWord(searchWord, columnIndex);
    assertEquals(expected, actual);
  }

  /** Looking for a word without a column index */
  @Test
  public void noIndex1() {

    String searchWord = "Cat";
    List<List<String>> expected = new ArrayList<>();
    expected.add(row1);

    List<List<String>> actual = testSearcher.searchFor(searchWord);
    assertEquals(expected, actual);
  }

  /** Looking for a word without a column index */
  @Test
  // done
  public void noIndex2() {
    String searchWord = "Frog";
    List<List<String>> expected = new ArrayList<>();
    expected.add(row3);
    List<List<String>> actual = testSearcher.searchFor(searchWord);
    assertEquals(expected, actual);
  }

  /** Looking for word in index where it doesn't occur */
  @Test
  // done
  public void notInIndex() {
    String searchWord = "Cat";
    List<List<String>> expected = new ArrayList<>();
    List<List<String>> actual = testSearcher.findWord(searchWord, 1);
    assertEquals(expected, actual);
  }

  /** Looking for word that doesn't exist */
  @Test
  // done
  public void notInFile() {
    String searchWord = "Zombie";
    List<List<String>> expected = new ArrayList<>();
    List<List<String>> actual = testSearcher.searchFor(searchWord);
    assertEquals(expected, actual);
  }

  /** Looking for word that occurs in multiple places */
  @Test
  public void inMultiplePlaces() {
    String searchWord = "Dog";
    List<List<String>> expected = new ArrayList<>();
    expected.add(row4);
    List<List<String>> actual = testSearcher.searchFor(searchWord);
    assertEquals(expected, actual);
  }

  /** Testing for out of bounds exception */
  @Test
  public void outofBounds() {
    assertThrows(
        IndexOutOfBoundsException.class,
        () -> {
          String searchWord = "Bunny";
          List<List<String>> expected = new ArrayList<>();
          List<List<String>> actual = testSearcher.findWord(searchWord, 8);
          assertEquals(expected, actual);
        });
  }

  /*
   * test to make sure null input is not accepted
   */
  @Test
  public void exceptionOnNullArgument() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          new CSVSearchUtility(null);
        });
  }

  /** User can use an index of -1 to indicate searching in any column */
  @Test
  public void SearchWithNegativeIndex() {
    String searchWord = "Mammal";
    List<List<String>> expected = new ArrayList<>();
    expected.add(row1);
    expected.add(row4);

    List<List<String>> actual = testSearcher.findWord(searchWord, -1);
    assertEquals(expected, actual);
  }

  /** Test default formatter */
  @Test
  public void searchThroughAllColumns() {

    StringReader stringReader =
        new StringReader(
            "Name,Animal,FavoriteFood\n"
                + "Frenchie,Cat,Salmon\n"
                + "Felix,Cat,Chicken\n"
                + "Fluffy,Dog,TeriyakiChicken\n");
    CSVParser parser = new CSVParser(stringReader, true);

    try {
      parser.Parse();
    } catch (Exception e) {
      assertTrue(false, "Parsing exception: " + e.getMessage());
    }

    List<List<String>> rawData = parser.getRawResults();

    CSVSearchUtility search = new CSVSearchUtility(rawData);

    List<List<String>> results;

    results = search.searchFor("Cat");
    assertTrue(results.size() == 2);

    results = search.searchFor("Dog");
    assertTrue(results.size() == 1);

    results = search.findWord("Cat", 1);
    assertTrue(results.size() == 2);

    results = search.searchFor("Beaver");
    assertTrue((results == null) || (results.size() == 0));

    results = search.findWord("Turtle", 0);
    assertTrue((results == null) || (results.size() == 0));

    System.out.println("All tests successfully completed");
  }
}
