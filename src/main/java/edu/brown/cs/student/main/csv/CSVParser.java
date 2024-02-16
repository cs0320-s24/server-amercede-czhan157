package edu.brown.cs.student.main.csv;

import edu.brown.cs.student.main.parser.CreatorFromRow;
import edu.brown.cs.student.main.parser.FactoryFailureException;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParser<T> {
  private Reader reader;
  private CreatorFromRow<T> creator;
  private List<List<String>> rawRows = null; // to store raw row data
  private List<T> processedRows = null; // to store processed row data
  private Boolean headers = false; // default is to not have headers
  private String commaString = ","; // default character to split on
  private Integer numColumns = 0;
  private String[] columnNames = null;

  /**
   * CSV Parser class constructor
   *
   * @param reader: a reader to read from data
   * @param creatorFromRow: row formatter
   * @param headers: indicates whether the data to be parsed has a header line
   * @param commaString: character to split on
   */
  public CSVParser(
      Reader reader, CreatorFromRow<T> creatorFromRow, String commaString, Boolean headers) {

    if (reader == null) {
      throw new IllegalArgumentException("Reader must not be null.");
    }

    if (creatorFromRow == null) {
      throw new IllegalArgumentException("Creator formatter must not be null.");
    }

    this.reader = reader;
    this.creator = creatorFromRow;
    this.headers = headers;
    this.commaString = commaString;
  }

  /**
   * default parsing method
   *
   * @throws Exception
   */
  public List<T> Parse() throws Exception {
    return parse(this.headers, this.commaString);
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
  public List<T> parse(Boolean headers, String commaType)
      throws FactoryFailureException, Exception {

    this.rawRows = new ArrayList<>(); // will eventually contain raw data
    this.processedRows = new ArrayList<T>(); // will eventually contain with formatted data
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
            this.numColumns = columns.length; // update number of columns
            addFormatRow(line, commaString);
          }
          firstLine = false;
        }
      }
    } catch (FactoryFailureException e) {
      throw e;

    } catch (Exception e) {
      throw e;
    }
    return processedRows;
  }

  /**
   * creates formatted creator objects out of column values of a single row
   *
   * @param line: input row to be split
   * @param commaString: character for row to be split on
   */
  private void addFormatRow(String line, String commaString) throws FactoryFailureException {
    String[] colNames = line.split(commaString);
    List<String> columnValues = Arrays.asList(colNames);

    this.rawRows.add(columnValues);
    T processedRow = this.creator.create(columnValues);
    this.processedRows.add(processedRow);
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
   * returns the processed rows
   *
   * @return list of processed rows
   */
  public List<T> getProcessedRows() {
    return this.processedRows;
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
