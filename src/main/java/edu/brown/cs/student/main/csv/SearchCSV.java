package edu.brown.cs.student.main.csv;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.common.APIException;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.CSVSearchResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import edu.brown.cs.student.main.parser.CSVSearchUtility;
import edu.brown.cs.student.main.parser.CreatorFromRow.*;
import edu.brown.cs.student.main.parser.DefaultFormatter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchCSV implements Route {

  private final String parserFile;
  // private static final String dirPath = "./data/";
  private static final String dirPath = "";

  public SearchCSV(String parserFile) {
    this.parserFile = parserFile;
  }

  @Override
  public Object handle(Request request, Response response) {

    try { // response.header("Content-Type", "application/json");

      Map<String, String[]> parameters = request.queryMap().toMap();
      String query = request.queryParams("query");
      String column = request.queryParams("column");
      String header = request.queryParams("header");

      checkQuery(query);
      Reader csvReader = new BufferedReader(new FileReader(dirPath + parserFile));
      DefaultFormatter defaultFormatter = new DefaultFormatter();
      Boolean headerBool;
      if (header.equals("true")) {
        headerBool = true;
      } else {
        headerBool = false;
      }
      CSVParser parser = new CSVParser(csvReader, headerBool);
      List<List<String>> result = search(query, parser, column);
      CSVSearchResponse csvResponse = new CSVSearchResponse(ResultInfo.success, result, parameters);
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<CSVSearchResponse> adapter = moshi.adapter(CSVSearchResponse.class);
      String successResponse = adapter.toJson(csvResponse);
      response.body(successResponse);
    } catch (FileNotFoundException e) {
      Map<String, String[]> parameters = request.queryMap().toMap();
      CSVResponse csvResponse =
          new CSVResponse(ResultInfo.file_not_found_failure, e.getMessage(), parameters);
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<CSVResponse> adapter = moshi.adapter(CSVResponse.class);
      String failureResponse = adapter.toJson(csvResponse);
      response.body(failureResponse);
    } catch (APIException e) {
      Map<String, String[]> parameters = request.queryMap().toMap();
      CSVResponse csvResponse = new CSVResponse(e.getResultInfo(), e.getMessage(), parameters);
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<CSVResponse> adapter = moshi.adapter(CSVResponse.class);
      String failureResponse = adapter.toJson(csvResponse);
      response.body(failureResponse);
    }

    return null;
  }

  private List<List<String>> search(String query, CSVParser parser, String column) {

    try {
      List<List<String>> rows = parser.Parse();
      List<List<String>> result;
      CSVSearchUtility searcher = new CSVSearchUtility(rows);
      if (column.matches("\\d+")) {
        Integer colIdx = Integer.parseInt(column, 10);
        result = searcher.findWord(query, colIdx);
      } else {
        result = searcher.findWord(query, column);
      }

      return result;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  private void checkQuery(String query) throws APIException {

    if (query == null || query.contains("-") || query.contains("_")) {
      throw new APIException(ResultInfo.bad_request_failure, "query parameter is missing");
    }
  }
}
