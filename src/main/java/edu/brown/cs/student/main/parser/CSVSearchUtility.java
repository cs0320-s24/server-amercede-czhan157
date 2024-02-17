package edu.brown.cs.student.main.parser;

import java.util.ArrayList;
import java.util.List;

public class CSVSearchUtility {

  private final List<List<String>> infos;

  /**
   * constructor for my CSVSearchUtility class
   *
   * @param infos the rows of information to search through
   */
  public CSVSearchUtility(List<List<String>> infos) {

    if (infos == null) {
      throw new IllegalArgumentException("Records/rows parameter cannot be null.");
    }

    this.infos = infos;
  }

  /**
   * look through all columns to find a string
   *
   * @param searchWord: the string to search for
   * @return list of rows containing target string
   */
  public List<List<String>> searchFor(String searchWord) {
    return findWord(searchWord, -1);
  }

  /**
   * finds the index of a column with a given name
   *
   * @param columnName name of the column to find
   * @return index of the column, or -1 if not found
   */
  private int findColumnIndex(String columnName) {
    List<String> headerRow = infos.get(0);
    for (int columnIndex = 0; columnIndex < headerRow.size(); columnIndex++) {
      if (headerRow.get(columnIndex).equals(columnName)) {

        return columnIndex;
      }
    }
    return -1; // column not found
  }

  /**
   * find a string in a column specified by name. this function should only be used when the input
   * reader object contains headers
   *
   * @param searchWord: the string to search for
   * @param columnName: name of the column to search in
   * @return list of rows containing the target string
   */
  public List<List<String>> findWord(String searchWord, String columnName) {

    if (this.infos.size() < 1) return null;

    // find the index of the column with the given name.

    int columnIndex;
    List<String> headerRow = this.infos.get(0);
    columnIndex = findColumnIndex(columnName);

    // search within valid column index, if found
    //
    if (columnIndex < headerRow.size()) {
      return findWord(searchWord, columnIndex);
    } else {
      return null;
    }
  }

  /**
   * find a string in a column specified by index
   *
   * @param searchWord: the string to search for
   * @return list of rows containing the target string
   */
  public List<List<String>> findWord(String searchWord, Integer columnIndex) {

    // instantiate the search results
    List<List<String>> searchResults = new ArrayList<>();

    for (List<String> row : this.infos) {
      if ((columnIndex >= 0) && row.get(columnIndex).equals(searchWord)) {
        searchResults.add(row);
      } else if (columnIndex == -1) {
        // if the column index is negative 1, no target index is specified
        for (String column : row) {
          if (column.equals(searchWord)) {
            searchResults.add(row);
          }
        }
      } else if (columnIndex > row.size() - 1) {
        System.out.println("note: column index too large, so no search is happening.");
      } else if (columnIndex < -1) {
        System.out.println("note: column index is less than -1, so no search is happening.");
      }
    }

    return searchResults;
  }
}
