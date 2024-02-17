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
  private boolean isCSVLoaded = false;

  public Server(Datasource state, String csvUtility) {

    // Register the handlers properly

    // CORS configuration
    Spark.after(
        (request, response) -> {
          System.out.println("31");
          System.out.println(request.queryMap().toMap());
          System.out.println("31");
          // System.out.println(request.headers());
          // System.out.println(response.body());
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });

    Spark.get(
        "loadcsv",
        (request, response) -> {
          isCSVLoaded = true;
          return new LoadCSV(csvUtility).handle(request, response);
        });

    Spark.get(
        "viewcsv",
        (request, response) -> { // Handler for viewing CSV
          if (isCSVLoaded) { // Check if CSV is loaded
            return new ViewCSV(csvUtility).handle(request, response); // Proceed to handle request
          } else {
            response.status(400); // If CSV is not loaded, return status 400 (Bad Request)
            return "CSV data not loaded yet.";
          }
        });

    Spark.get(
        "searchcsv",
        (request, response) -> { // Handler for searching CSV
          if (isCSVLoaded) { // Check if CSV is loaded
            return new SearchCSV(csvUtility).handle(request, response); // Proceed to handle request
          } else {
            response.status(400); // If CSV is not loaded, return status 400 (Bad Request)
            return "CSV data not loaded yet.";
          }
        });

    Spark.get("census", new BroadbandHandler(state));
    // Handle unspecified routes
    Spark.get(
        "*",
        (request, response) -> {
          response.header("Content-Type", "application/json");
          return "{}"; // Return empty JSON object for unspecified routes
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
    // String csvUtility = "./data/stars/stardata.csv";
    String csvUtility = "./data/stars/stardata.csv";
    int port = 3232;
    Spark.port(port);
    Logger.getLogger("").setLevel(Level.WARNING);

    // Initialize and start the server
    new Server(state, csvUtility);
    Spark.init();
    Spark.awaitInitialization();
    System.out.println("Server started at http://localhost:" + port);
  }
}
