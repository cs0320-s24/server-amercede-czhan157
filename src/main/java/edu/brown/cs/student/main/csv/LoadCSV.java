package edu.brown.cs.student.main.csv;

import edu.brown.cs.student.main.common.CSVException;
import edu.brown.cs.student.main.csv.CSVParser;
import edu.brown.cs.student.main.common.ResultInfo;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoadCSV implements Route {
    private static final String dirPath = "./data/";
    // have user potentially input the allowed directory path
    //
    private final String csvFile;

    public LoadCSV(String csvFile) {
        this.csvFile = csvFile;
    }

    /**
   * Handles loadcsv endpoint Sets success response if no APIFailureException caught Sets failure
   * response otherwise
   *
   * @param request The request object providing information about the HTTP request
   * @param response The response object providing functionality for modifying the response
   * @return null
   */
  @Override
  public Object handle(Request request, Response response) {
    response.header("Content-Type", "application/json");
    // fetching parameters
    Map<String, String[]> parameters = request.queryMap().toMap();
    String filePath = request.queryParams("filepath");
    String hasHeader = request.queryParams("header");
    try {
      checkFilePath(filePath);
      checkHeader(hasHeader);
      try {
        Reader reader = new FileReader(dirPath + filePath);
        CSVParser<List<List<String>>> csvParser;
        // appending success response
        String successResponse =
            
            new CSVResponse(ResultInfo.success, "Successfully loaded CSV", parameters).serialize();
        response.body(successResponse);
      } catch (IOException e) {
        throw new CSVException(
            ResultInfo.bad_request_failure, "Failed to read CSV");
      }
    } catch (CSVException e) {
      // appending failure response
      String failureResponse =
          new CSVResponse(e.getResultInfo(), e.getMessage(), parameters).serialize();
      response.body(failureResponse);
    }
    return null;
  }

  /**
   * Checks if filePath is null, does not exist or attempts to access upward directory
   *
   * @param filePath from http request params
   * @throws APIFailureException pass on to handle
   */
  private void checkFilePath(String filePath) throws CSVException {
    if (filePath == null || filePath == "") {
      throw new  CSVException(
        ResultInfo.bad_request_failure, "Error: file path is not provided.");
    }
    if (filePath.contains("..")) {
      throw new CSVException(ResultInfo.bad_request_failure, "Error: file path cannot exist outside main directory");
    }
    // check file is exists
    if (!Files.exists(Paths.get(dirPath, filePath))) {
      throw new CSVException(ResultInfo.datasource_failure, "Error: file path does not exist.");
    }
  }

  private void checkHeader(String hasHeader) throws CSVException {
    if (Objects.equals(hasHeader, "true") | Objects.equals(hasHeader, "false")) return;
    throw new CSVException(
        ResultInfo.bad_request_failure, "you must indicate whether there is a header via true or false");
  }
}

    // server isn't main function, but if it were: server is the manager, controls all
    // endpoints it has - endpoints are classes servers will connect to
    // you need to have it implement route -- route transforms normal class into an endpoin
    // route will make you create a handle method




