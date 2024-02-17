package edu.brown.cs.student.main.csv;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.common.CSVException;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.CSVSearchResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class ViewCSV implements Route {
  private String parserFile;
  private static final String dirPath = "";

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

      Reader csvReader = new BufferedReader(new FileReader(dirPath + this.parserFile));

      CSVParser parser = new CSVParser(csvReader, true);
      List<List<String>> rawRows = parser.Parse();
      CSVSearchResponse csvResponse =
          new CSVSearchResponse(ResultInfo.success, rawRows, parameters);
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<CSVSearchResponse> adapter = moshi.adapter(CSVSearchResponse.class);
      String successResponse = adapter.toJson(csvResponse);
      response.body(successResponse);

    } catch (CSVException e) {
      CSVResponse csvResponse = new CSVResponse(e.getResultInfo(), e.getMessage(), parameters);
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<CSVResponse> adapter = moshi.adapter(CSVResponse.class);
      String successResponse = adapter.toJson(csvResponse);
      response.body(successResponse);
    } catch (FileNotFoundException e) {
      CSVResponse failure =
          new CSVResponse(ResultInfo.file_not_found_failure, e.getMessage(), parameters);
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<CSVResponse> adapter = moshi.adapter(CSVResponse.class);
      String failureResponse = adapter.toJson(failure);
      response.body(failureResponse);
    } catch (Exception e) {
      CSVResponse failure =
          new CSVResponse(ResultInfo.internal_failure, e.getMessage(), parameters);
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<CSVResponse> adapter = moshi.adapter(CSVResponse.class);
      String failureResponse = adapter.toJson(failure);
      response.body(failureResponse);
    }
    return null;
  }
}
