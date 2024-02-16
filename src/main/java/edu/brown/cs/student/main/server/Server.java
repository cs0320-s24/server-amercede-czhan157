package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.csv.*;
import edu.brown.cs.student.main.datasource.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import spark.Spark;

public class Server {
  private static Datasource state;

  public Server(Datasource state) {

    String csvUtility = new String();

    Spark.get("loadcsv", new LoadCSV(csvUtility));
    Spark.get("viewCSV", new ViewCSV(csvUtility));
    Spark.get("loadcsv", new SearchCSV(csvUtility));
    Spark.get("census", new BroadbandHandler(state));

    Spark.get(
        "*",
        (request, response) -> {
          response.header("Content-Type", "application/json");
          response.body();

          return null;
        });
  }

  public static void main(String[] args)
      throws URISyntaxException, IOException, InterruptedException {
    Datasource state = new CensusAPI();

    int port = 3232;
    Spark.port(port);
    after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
          response.header("Access-Control-Allow-Origin", "loadcsv");
          response.header("Access-Control-Allow-Origin", "census");
          response.header("Access-Control-Allow-Origin", "viewcsv");
          response.header("Access-Control-Allow-Origin", "searchcsv");
        });

    Spark.get("broadband", new BroadbandHandler(state));

    // I need to find where this is held
    String broadbandAsJson = BroadbandData.readInJson("data/broadband.json");
    List<Broadband> broadbandList = new ArrayList<>();
    try {
      broadbandList = BroadbandData.deserializeBroadbandJson(broadbandAsJson);
    } catch (Exception e) {
      // See note in ActivityHandler about this broad Exception catch... Unsatisfactory, but gets
      // the job done in the gearup where it is not the focus.
      e.printStackTrace();
      System.err.println("Errored while deserializing the broadbandList");
    }
    Spark.init();
    Spark.awaitInitialization();
    System.out.println("Server started at http://localhost:" + port);
  }
}
