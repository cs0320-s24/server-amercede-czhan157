package edu.brown.cs.student.main.csv;

import edu.brown.cs.student.main.common.APIException;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import edu.brown.cs.student.main.parser.CreatorFromRow.*;
import edu.brown.cs.student.main.parser.DefaultFormatter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchCSV implements Route {

  private final String parserFile;

  public SearchCSV(String parserFile) {
    this.parserFile = parserFile;
  }

  @Override
  public Object handle(Request request, Response response) {
    // use a responseMap
    response.header("Content-Type", "application/json");
    // fetching parameters
    Map<String, String[]> parameters = request.queryMap().toMap();
    String query = request.queryParams("query");
    try {

      checkQuery(query);
      Map<String, String[]> params = request.queryMap().toMap();
      Reader csvReader = new BufferedReader(new FileReader(this.parserFile));
      DefaultFormatter defaultFormatter = new DefaultFormatter();

      CSVParser<List<List<String>>> parser = new CSVParser(csvReader, defaultFormatter, ",", true);
      List<List<String>> result = search(query, parser);
      String successResponse =
          new CSVResponse(ResultInfo.success, "success", parameters).serialize();
      response.body(successResponse);
    } catch (APIException e) {
      // appending failure response
      String failureResponse =
          new CSVResponse(e.getResultInfo(), e.getMessage(), parameters).serialize();
      response.body(failureResponse);
    } catch (FileNotFoundException e) {
      // appending failure response
      String failureResponse =
          new CSVResponse(ResultInfo.internal_failure, e.getMessage(), parameters).serialize();
      response.body(failureResponse);
    }
    return null;
  }

  private List<List<String>> search(String query, CSVParser<List<List<String>>> parser) {

    List<List<String>> result = new ArrayList<>();
    List<List<String>> rows = parser.getRawResults();
    for (List<String> row : rows) {
      if (row.contains(query)) {
        result.add(row);
      }
    }

    return result;
  }

  private void checkQuery(String query) throws APIException {

    if (query == null) {
      throw new APIException(ResultInfo.bad_request_failure, "query parameter is missing");
    }
  }
}
