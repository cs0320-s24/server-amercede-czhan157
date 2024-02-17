package edu.brown.cs.student.main.csv;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.common.CSVException;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoadCSV implements Route {
  private static final String dirPath = "";
  // have user potentially input the allowed directory path
  //
  private final String csvFile;

  public LoadCSV(String csvFile) {
    this.csvFile = csvFile;
  }

  @Override
  public Object handle(Request request, Response response) {
    System.out.println("not yet");
    response.header("Content-Type", "application/json");
    // fetching parameters
    Map<String, String[]> parameters = request.queryMap().toMap();
    System.out.println("in loadcsv");
    // System.out.println(parameters.get("filepath").toString());
    // System.out.println(parameters.get("header").toString());
    String filePath = request.queryParams("filepath");
    String hasHeader = request.queryParams("header");
    System.out.println(filePath);
    System.out.println(hasHeader);
    System.out.println("retrieved");
    try {
      checkFilePath(filePath);
      checkHeader(hasHeader);
      System.out.println("all valid");
      try {
        Reader reader = new FileReader(dirPath + filePath);
        CSVParser csvParser;
        Reader csvReader = new BufferedReader(new FileReader(filePath));

        CSVParser parser = new CSVParser(csvReader, true);

        CSVResponse csvResponse =
            new CSVResponse(ResultInfo.success, "Successfully loaded CSV!", parameters);
        Moshi moshi = new Moshi.Builder().build();
        String successResponse = moshi.adapter(CSVResponse.class).toJson(csvResponse);

        response.body(successResponse);
        System.out.println(response.toString());
      } catch (IOException e) {
        throw new CSVException(ResultInfo.bad_request_failure, "Failed to read CSV");
      }
    } catch (CSVException e) {
      // appending failure response
      CSVResponse csvResponse = new CSVResponse(e.getResultInfo(), e.getMessage(), parameters);
      Moshi moshi = new Moshi.Builder().build();
      System.out.println("CSVException");
      JsonAdapter<CSVResponse> adapter = moshi.adapter(CSVResponse.class);
      String failureResponse = adapter.toJson(csvResponse);
      response.body(failureResponse);
    }
    return null;
  }

  private void checkFilePath(String filePath) throws CSVException {
    if (filePath == null || filePath == "") {
      throw new CSVException(ResultInfo.bad_request_failure, "Error: file path is not provided.");
    }
    if (filePath.contains("..")) {
      throw new CSVException(
          ResultInfo.bad_request_failure, "Error: file path cannot exist outside main directory");
    }
    // check file is exists
    if (!Files.exists(Paths.get(dirPath, filePath))) {
      System.out.println("file not found for whatever reason");
      System.out.println(Paths.get(dirPath, filePath).toString());
      throw new CSVException(ResultInfo.file_not_found_failure, "Error: file path does not exist.");
    }

    if (!(filePath.length() > 6 & filePath.startsWith("./data/"))) {
      throw new CSVException(
          ResultInfo.bad_request_failure, "Error: file pathcannot exist outside main director");
    }
  }

  private void checkHeader(String hasHeader) throws CSVException {
    if (Objects.equals(hasHeader, "true") | Objects.equals(hasHeader, "false")) return;
    throw new CSVException(
        ResultInfo.bad_request_failure,
        "you must indicate whether there is a header via true or false");
  }
}
