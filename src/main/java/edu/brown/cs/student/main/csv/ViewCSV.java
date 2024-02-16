package edu.brown.cs.student.main.csv;
import edu.brown.cs.student.main.common.CSVException;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import edu.brown.cs.student.main.common.BroadbandResponse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;


public class ViewCSV implements Route{
    private String parserFile;
    public ViewCSV(String parserFile){
        this.parserFile = parserFile;
    }

    
  @Override
  public Object handle(Request request, Response response) {
    response.header("Content-Type", "application/json");
    // fetching parameters
    Map<String, String[]> parameters = request.queryMap().toMap();
    try {
      if (!parameters.isEmpty())
        throw new CSVException(
          ResultInfo.bad_request_failure, "viewcsv should not have any parameters.");
      Reader reader = new FileReader(this.parserFile);
        CSVParser<List<List<String>>> csvParser;
      String successResponse =
          new CSVResponse(
                  ResultInfo.success, "SUCCESS", parameters)
              .serialize();
      response.body(successResponse);
    } catch (CSVException e) {
      // appending failure response
      String failureResponse =
          new CSVResponse(e.getResultInfo(), e.getMessage(), parameters).serialize();
      response.body(failureResponse);
    }
    catch (FileNotFoundException e) {
      // appending failure response
      String failureResponse = "File not found!";
      response.body(failureResponse);
    }
    return null;
  }
}

// Mesh Network Community Coalition: "As a developer calling your web API,
// I can make API requests to load, view, or search the contents of a CSV file by calling the `loadcsv`, `viewcsv`
// or `searchcsv` endpoints. For `loadcsv`, I will provide the path of the CSV file to load (on the backend)."

// Acceptance Criteria: The `loadcsv` API query for CSV data takes a file path. At most one
// CSV dataset is loaded at any time, and using `viewcsv` or `searchcsv` CSV queries without a
// CSV loaded must produce an error API response, but not halt the server. (See the API specification
// your server must follow below.) The requirements for search include the ability to search by column
//  index, the ability to search by column header, as well as the ability to search across all columns.
//  A command-line entry point is intended to start the server (i.e. clicking the green play button),
//  displaying minimal output, such as “Server started” and an instructional line. You will aim to achieve
//  this through the use of a run script. Similarly to the CSV sprint, this is provided in the stencil code.
//   This ensures that interaction with the server’s functionalities is primarily through the API endpoints.

// You should use your search code from CSV for this, possibly with modifications.
// For CSCI 1340: incoming API requests may use the nested and/or/not boolean query pattern
// from queries in the CSV sprint.
