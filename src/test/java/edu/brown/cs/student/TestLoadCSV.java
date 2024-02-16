package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import org.junit.jupiter.api.Test;

public class TestLoadCSV extends TestSetup {
  @Test
  public void testHost() {
    try {
      HttpURLConnection conn = tryRequest("loadcsv");
      assertEquals(200, conn.getResponseCode());
      conn.disconnect();
    } catch (IOException e) {
      fail(e);
    }
  }
}
