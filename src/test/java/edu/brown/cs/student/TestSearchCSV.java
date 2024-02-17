package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.CSVSearchResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import java.io.IOException;
import java.net.HttpURLConnection;
import okio.Buffer;
import org.junit.jupiter.api.Test;

public class TestSearchCSV extends TestSetup {

  /** Ensure Searchcsv is properly routed * */
  @Test
  public void testWebRequestLoadCsv() {
    try {
      HttpURLConnection c = tryRequest("/searchcsv");
      assertEquals(200, c.getResponseCode());
      c.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  /** Ensure Searchcsv properly handles searching before loading * */
  @Test
  public void testCsvNotLoaded() {
    try {
      HttpURLConnection c = tryRequest("/searchcsv?");
      assertEquals(200, c.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVResponse response =
          moshi.adapter(CSVResponse.class).fromJson(new Buffer().readFrom(c.getInputStream()));
      assertEquals(ResultInfo.bad_request_failure, response.result());
      c.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  /**
   * Helper function to load csv before each call to searchcsv
   *
   * @param filePath desired filepath
   * @param header "true" or "false" indicating if filePath has header
   */
  private void load(String filePath, Boolean header) {
    try {
      HttpURLConnection c = tryRequest("/loadcsv?filepath=" + filePath + "&header=" + header);
      assertEquals(200, c.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVResponse response =
          moshi.adapter(CSVResponse.class).fromJson(new Buffer().readFrom(c.getInputStream()));
      c.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  /** Ensure Searchcsv properly handles invalid no parameters * */
  @Test
  public void testSearchNoParams() {
    load("src/test/java/edu/brown/cs/student/data/stars/stardata.csv", true);
    try {
      HttpURLConnection conn = tryRequest("searchcsv");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVResponse response =
          moshi.adapter(CSVResponse.class).fromJson(new Buffer().readFrom(conn.getInputStream()));
      assertEquals(ResultInfo.bad_request_failure, response.result());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  /** Ensure Searchcsv properly handles bad urls * */
  @Test
  public void testSearchCsvBadURL() {
    load("./data/stars/stardata.csv", false);
    try {
      HttpURLConnection conn = tryRequest("/searchcsv?query=bad_parameters");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVResponse response =
          moshi.adapter(CSVResponse.class).fromJson(new Buffer().readFrom(conn.getInputStream()));
      // System.out.println(response);
      assertEquals(ResultInfo.bad_request_failure, response.result());
      conn.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
      fail(e);
    }
  }

  /** Ensure Searchcsv returns successful response if provided valid inputs * */
  @Test
  public void testSearchCsvSuccess() {
    load("./data/stars/stardata.csv", true);
    try {
      HttpURLConnection conn = tryRequest("searchcsv?query=Sol&column=1&header=true");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVSearchResponse response =
          moshi
              .adapter(CSVSearchResponse.class)
              .fromJson(new Buffer().readFrom(conn.getInputStream()));
      assertEquals(ResultInfo.success, response.result());
      assertEquals(1, response.data().size());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  @Test
  public void testSearchCsvSuccessWrongColumn() {
    load("./data/stars/stardata.csv", true);
    try {
      HttpURLConnection conn = tryRequest("searchcsv?query=Lemma&column=2&header=true");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVSearchResponse response =
          moshi
              .adapter(CSVSearchResponse.class)
              .fromJson(new Buffer().readFrom(conn.getInputStream()));
      assertEquals(ResultInfo.success, response.result());
      assertEquals(0, response.data().size());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  @Test
  public void testSearchCsvSuccessColumnName() {
    load("./data/stars/stardata.csv", true);
    try {
      HttpURLConnection conn = tryRequest("searchcsv?query=Lemma&column=ProperName&header=true");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVSearchResponse response =
          moshi
              .adapter(CSVSearchResponse.class)
              .fromJson(new Buffer().readFrom(conn.getInputStream()));
      assertEquals(ResultInfo.success, response.result());
      assertEquals(1, response.data().size());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  @Test
  public void testSearchCsvSuccessBadColName() {
    load("./data/stars/stardata.csv", true);
    try {
      HttpURLConnection conn = tryRequest("searchcsv?query=Lemma&column=StarID&header=true");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVSearchResponse response =
          moshi
              .adapter(CSVSearchResponse.class)
              .fromJson(new Buffer().readFrom(conn.getInputStream()));
      assertEquals(ResultInfo.success, response.result());
      assertEquals(0, response.data().size());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  @Test
  public void testSearchHeaderDifference() {
    load("./data/stars/stardata.csv", true);
    try {
      HttpURLConnection conn = tryRequest("searchcsv?query=StarID&column=0&header=false");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVSearchResponse response =
          moshi
              .adapter(CSVSearchResponse.class)
              .fromJson(new Buffer().readFrom(conn.getInputStream()));
      assertEquals(ResultInfo.success, response.result());
      assertEquals(1, response.data().size());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  @Test
  public void testSearchNoColumn() {
    load("./data/stars/stardata.csv", true);
    try {
      HttpURLConnection conn = tryRequest("searchcsv?query=Emma&column=-1&header=true");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVSearchResponse response =
          moshi
              .adapter(CSVSearchResponse.class)
              .fromJson(new Buffer().readFrom(conn.getInputStream()));
      assertEquals(ResultInfo.success, response.result());
      assertEquals(1, response.data().size());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }
}
