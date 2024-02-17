package edu.brown.cs.student;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import edu.brown.cs.student.main.csv.LoadCSV;
import edu.brown.cs.student.main.csv.SearchCSV;
import edu.brown.cs.student.main.csv.ViewCSV;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import spark.Spark;

public class TestSetup {
  @BeforeAll
  public static void setup_before_everything() {
    Spark.port(3232);
    Logger.getLogger("").setLevel(Level.WARNING); // empty name = root logger
  }

  @BeforeEach
  public void setup() {
    Spark.after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });
    String csv = "stars/stardata.csv";
    Spark.get("loadcsv", new LoadCSV(csv));
    Spark.get("viewcsv", new ViewCSV(csv));
    Spark.get("searchcsv", new SearchCSV(csv));
    Spark.get(
        "*",
        (request, response) -> {
          response.header("Content-Type", "application/json");

          CSVResponse csvResponse =
              new CSVResponse(ResultInfo.bad_request_failure, "Unexpected endpoints.", null);
          System.out.println("happening in setup");
          Moshi moshi = new Moshi.Builder().build();

          JsonAdapter<CSVResponse> adapter = moshi.adapter(CSVResponse.class);
          String failureResponse = adapter.toJson(csvResponse);
          response.body(failureResponse);

          return null;
        });
    Spark.init();
    Spark.awaitInitialization();
  }

  @AfterEach
  public void teardown() {
    Spark.stop();
    Spark.awaitStop();
  }

  protected HttpURLConnection tryRequest(String apiCall) throws IOException {
    URL requestURL = new URL("http://localhost:" + Spark.port() + "/" + apiCall);
    HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();
    conn.connect();
    return conn;
  }
}
