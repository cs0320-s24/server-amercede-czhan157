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

public class TestViewCSV extends TestSetup {
  /** Ensure that viewcsv endpoint is properly routed * */
  @Test
  public void testRequestViewCsv() {
    try {
      HttpURLConnection c = tryRequest("/viewcsv");
      assertEquals(200, c.getResponseCode());
      c.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }
  /** Ensure proper handling if CSV not loaded before * */
  @Test
  public void testViewNotLoaded() {
    try {
      HttpURLConnection c = tryRequest("/viewcsv");
      assertEquals(200, c.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVResponse response =
          moshi.adapter(CSVResponse.class).fromJson(new Buffer().readFrom(c.getInputStream()));
      assertEquals(ResultInfo.success, response.result());
      c.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  /** Test if viewcsv successfully returns the csv.* */
  @Test
  public void testViewAfterLoading() {
    try {
      HttpURLConnection conn =
          tryRequest("/loadcsv?filepath=./data/stars/stardata.csv&header=true");
      assertEquals(200, conn.getResponseCode());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
    try {
      HttpURLConnection conn = tryRequest("/viewcsv");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVSearchResponse response =
          moshi
              .adapter(CSVSearchResponse.class)
              .fromJson(new Buffer().readFrom(conn.getInputStream()));
      assertEquals(ResultInfo.success, response.result());
      assertEquals(119618, response.data().size());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }
}
