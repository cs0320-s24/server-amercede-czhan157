package edu.brown.cs.student.main.csv;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParser {
  private Reader reader;
  private List<List<String>> rawRows = null; // to store raw row data
  private Boolean headers = false; // default is to not have headers
  private String commaString = ","; // default character to split on
  private Integer numColumns = 0;
  private String[] columnNames = null;

  /**
   * CSV Parser class constructor
   *
   * @param reader: a reader to read from data
   * @param headers: indicates whether the data to be parsed has a header line
   */
  public CSVParser(Reader reader, Boolean headers) {

    if (reader == null) {
      throw new IllegalArgumentException("Reader must not be null.");
    }

    this.reader = reader;
    this.headers = headers;
  }

  /**
   * default parsing method
   *
   * @throws Exception
   */
  public List<List<String>> Parse() throws Exception {
    return parse(this.headers, ",");
  }

  private void processHeaders(String headerLine, String commaString) {
    columnNames = headerLine.split(commaString); // split based on splitting character
    rawRows.add(Arrays.asList(columnNames)); // raw rows contain headers if they are available
    this.numColumns = columnNames.length; // update number of columns
  }

  /**
   * parsing function allowing for more user customization
   *
   * @param headers: indicates whether reader object contains header
   * @param commaType: string to split on
   * @throws Exception
   */
  public List<List<String>> parse(Boolean headers, String commaType) throws Exception {

    this.rawRows = new ArrayList<>(); // will eventually contain raw data

    this.columnNames = null;
    this.numColumns = 0;

    try (BufferedReader bufReader = new BufferedReader(reader)) {
      boolean firstLine = true;
      String line;
      while ((line = bufReader.readLine()) != null) {
        if (line.length() >= 1) {
          if (headers && firstLine) {
            processHeaders(line, commaType);
          } else {
            String[] columns = new String[2048];
            columns = line.split(commaString); // split based on splitting character
            List<String> columnList = Arrays.asList(columns);
            rawRows.add(columnList);
            this.numColumns = columns.length; // update number of columns
          }
          firstLine = false;
        }
      }
    } catch (Exception e) {
      throw e;
    }
    return rawRows;
  }

  /**
   * returns the raw rows
   *
   * @return list of default formatted rows
   */
  public List<List<String>> getRawResults() {
    return this.rawRows;
  }

  /**
   * returns column names
   *
   * @return array of all column names if there was a header
   */
  public String[] getColumnNames() {
    return this.columnNames;
  }

  /**
   * returns the total number of rows
   *
   * @return number of rows.
   */
  public Integer getNumRows() {
    if (this.headers) {
      return this.rawRows.size() - 1;
    } else {
      return this.rawRows.size();
    }
  }

  /**
   * returns the total number of columns
   *
   * @return number of columns
   */
  public Integer getColumnCount() {
    return this.numColumns;
  }
}
