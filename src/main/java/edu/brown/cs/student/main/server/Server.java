package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.csv.*;
import edu.brown.cs.student.main.datasource.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Spark;

public class Server {
  private static Datasource state;

  public Server(Datasource state, String csvUtility) {

    // Register the handlers properly
    Spark.get("loadcsv", new LoadCSV(csvUtility));
    Spark.get("viewcsv", new ViewCSV(csvUtility));
    Spark.get("searchcsv", new SearchCSV(csvUtility));
    Spark.get("census", new BroadbandHandler(state));

    // CORS configuration
    Spark.before(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
          response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
          response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

    // Handle unspecified routes
    Spark.get(
        "*",
        (request, response) -> {
          response.header("Content-Type", "application/json");
          return "{}"; // Return empty JSON object for unspecified routes
        });
  }

  public static void main(String[] args)
      throws URISyntaxException, IOException, InterruptedException {

    Datasource state = new CensusAPI();
    String csvUtility = "./data/stars/stardata.csv";
    int port = 3232;
    Spark.port(port);
    Logger.getLogger("").setLevel(Level.WARNING);

    // Initialize and start the server
    new Server(state, csvUtility);
    System.out.println("Server started at http://localhost:" + port);
  }
}
