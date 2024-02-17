package edu.brown.cs.student.main.csv;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.common.CSVException;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import edu.brown.cs.student.main.parser.DefaultFormatter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoadCSV implements Route {
  // private static final String dirPath = "./src/test/java/edu/brown/cs/student/data/";
  private static final String dirPath = "";
  // have user potentially input the allowed directory path
  //
  private final String csvFile;

  public LoadCSV(String csvFile) {
    this.csvFile = csvFile;
  }

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
        Reader csvReader = new BufferedReader(new FileReader(filePath));
        DefaultFormatter defaultFormatter = new DefaultFormatter();

        CSVParser<List<List<String>>> parser =
            new CSVParser(csvReader, defaultFormatter, ",", true);

        CSVResponse csvResponse =
            new CSVResponse(ResultInfo.success, "Successfully loaded CSV!", parameters);
        Moshi moshi = new Moshi.Builder().build();
        String successResponse = moshi.adapter(CSVResponse.class).toJson(csvResponse);

        response.body(successResponse);
      } catch (IOException e) {
        throw new CSVException(ResultInfo.bad_request_failure, "Failed to read CSV");
      }
    } catch (CSVException e) {
      // appending failure response
      CSVResponse csvResponse = new CSVResponse(e.getResultInfo(), e.getMessage(), parameters);
      Moshi moshi = new Moshi.Builder().build();

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
      throw new CSVException(ResultInfo.datasource_failure, "Error: file path does not exist.");
    }
  }

  private void checkHeader(String hasHeader) throws CSVException {
    if (Objects.equals(hasHeader, "true") | Objects.equals(hasHeader, "false")) return;
    throw new CSVException(
        ResultInfo.bad_request_failure,
        "you must indicate whether there is a header via true or false");
  }
}

    // server isn't main function, but if it were: server is the manager, controls all
    // endpoints it has - endpoints are classes servers will connect to
    // you need to have it implement route -- route transforms normal class into an endpoin
    // route will make you create a handle method
