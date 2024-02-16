package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.common.CSVResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import okio.Buffer;
import org.junit.jupiter.api.Test;

public class TestLoadCSV extends TestSetup {
  @Test
  public void testHostLoadCSV() {
    try {
      HttpURLConnection conn = tryRequest("loadcsv");
      assertEquals(200, conn.getResponseCode());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }

  @Test
  public void testLoadCsvNoParameters() {
    try {
      HttpURLConnection conn = tryRequest("loadcsv");
      assertEquals(200, conn.getResponseCode());
      Moshi moshi = new Moshi.Builder().build();
      CSVResponse response =
          moshi.adapter(CSVResponse.class).fromJson(new Buffer().readFrom(conn.getInputStream()));
      assertThrows(
          NullPointerException.class,
          () -> {
            response.result();
          });
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }
}
