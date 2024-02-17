package edu.brown.cs.student.main.csv;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.common.CSVException;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import edu.brown.cs.student.main.parser.DefaultFormatter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class ViewCSV implements Route {
  private String parserFile;
  private static final String dirPath = "./data/";

  public ViewCSV(String parserFile) {
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
      CSVParser csvParser;

      Reader csvReader = new BufferedReader(new FileReader(dirPath + parserFile));
      DefaultFormatter defaultFormatter = new DefaultFormatter();

      CSVParser parser = new CSVParser(csvReader, true);

      CSVResponse csvResponse = new CSVResponse(ResultInfo.success, "SUCCESS", parameters);
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<CSVResponse> adapter = moshi.adapter(CSVResponse.class);
      String successResponse = adapter.toJson(csvResponse);
      response.body(successResponse);
    } catch (CSVException e) {
      CSVResponse csvResponse = new CSVResponse(e.getResultInfo(), e.getMessage(), parameters);
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<CSVResponse> adapter = moshi.adapter(CSVResponse.class);
      String successResponse = adapter.toJson(csvResponse);
      response.body(successResponse);
    } catch (FileNotFoundException e) {
      // appending failure response
      String failureResponse = "File not found!";
      response.body(failureResponse);
    }
    return null;
  }
}
