package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.common.CSVResponse;
import edu.brown.cs.student.main.common.ResultInfo;
import java.io.IOException;
import java.net.HttpURLConnection;
import okio.Buffer;
import org.junit.jupiter.api.Test;

public class TestLoadCSV extends TestSetup {
  @Test
  public void testHost() {
    try {
      HttpURLConnection c = tryRequest("loadcsv");
      assertEquals(200, c.getResponseCode());
      c.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  /** Test to make sure that valid filepaths can be properly loaded * */
  @Test
  public void testLoadCsvSuccess() {
    try {
      HttpURLConnection c = tryRequest("loadcsv?filepath=./data/stars/stardata.csv&header=true");

      Moshi moshi = new Moshi.Builder().build();
      CSVResponse response =
          moshi.adapter(CSVResponse.class).fromJson(new Buffer().readFrom(c.getInputStream()));

      assertEquals("success", response.result().toString());
      assertEquals(200, c.getResponseCode());
      assertEquals("./data/stars/stardata.csv", response.params().get("filepath")[0]);
      c.disconnect();
    } catch (IOException e) {
      System.out.println(e);
      fail(e);
    }
  }

  /** test for proper handling of no filepath * */
  @Test
  public void testLoadCsvFailWithOutFilepathParam() {
    try {
      HttpURLConnection c = tryRequest("/loadcsv?header=false");
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

  /** test for proper handling of no parameters * */
  @Test
  public void testLoadCsvFailWithOutParameters() {
    try {
      HttpURLConnection c = tryRequest("/loadcsv");
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
  /** test for proper handling of no header parameter * */
  @Test
  public void testLoadCsvFailWithOutHeaderParam() {
    try {
      HttpURLConnection c =
          tryRequest(
              "/loadcsv?filepath=src/test/java/edu/brown/cs/student/data/stars/stardata.csv");
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

  /** test for proper handling of invalid filepath * */
  @Test
  public void testLoadCsvFail() {
    try {
      HttpURLConnection c = tryRequest("/loadcsv?filepath=stars/xxxx.csv&header=false");
      assertEquals(200, c.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVResponse response =
          moshi.adapter(CSVResponse.class).fromJson(new Buffer().readFrom(c.getInputStream()));
      assertEquals(ResultInfo.file_not_found_failure, response.result());
      c.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  /** test for proper handling of filepath outside directory * */
  @Test
  public void testBadFilePath() {
    try {
      HttpURLConnection c =
          tryRequest("/loadcsv?filepath=stars/../../data/stardata.csv&header=true");
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
}
