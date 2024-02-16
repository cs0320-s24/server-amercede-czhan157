package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.csv.*;
import edu.brown.cs.student.main.datasource.*;
import java.io.IOException;
import java.net.URISyntaxException;
import spark.Spark;

public class Server {
  private static Datasource state;

  //   public Server(Datasource state) {

  //     String csvUtility = "data/stars/stardata.csv";

  //     Spark.get("/loadcsv", new LoadCSV(csvUtility));
  //     Spark.get("/viewCSV", new ViewCSV(csvUtility));
  //     Spark.get("/loadcsv", new SearchCSV(csvUtility));
  //     Spark.get("/census", new BroadbandHandler(state));

  //     Spark.get(
  //         "*",
  //         (request, response) -> {
  //           response.header("Content-Type", "application/json");
  //           response.body();

  //           return null;
  //         });
  //   }
  public Server(Datasource state) {
    String csvUtility = "data/stars/stardata.csv";

    // Register the handlers properly
    Spark.get("/loadcsv", new LoadCSV(csvUtility));
    Spark.get("/viewcsv", new ViewCSV(csvUtility));
    Spark.get("/searchcsv", new SearchCSV(csvUtility));
    Spark.get("/census", new BroadbandHandler(state));

    // CORS configuration
    Spark.before(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
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
    // Datasource state = new CensusAPI();

    // int port = 3232;
    // Spark.port(port);
    // after(
    //     (request, response) -> {
    //       response.header("Access-Control-Allow-Origin", "*");
    //       // response.header("Access-Control-Allow-Methods", "*");
    //       response.header("Access-Control-Allow-Origin", "loadcsv");
    //       response.header("Access-Control-Allow-Origin", "census");
    //       response.header("Access-Control-Allow-Origin", "viewcsv");
    //       response.header("Access-Control-Allow-Origin", "searchcsv");
    //     });

    // Spark.get("broadband", new BroadbandHandler(state));

    // // I need to find where this is held
    // // String broadbandAsJson = BroadbandData.readInJson("data/broadband.json");
    // // List<Broadband> broadbandList = new ArrayList<>();
    // // try {
    // //  broadbandList = BroadbandData.deserializeBroadbandJson(broadbandAsJson);
    // // } catch (Exception e) {
    // // See note in ActivityHandler about this broad Exception catch... Unsatisfactory, but gets
    // // the job done in the gearup where it is not the focus.
    // //  e.printStackTrace();
    // //  System.err.println("Errored while deserializing the broadbandList");
    // // }
    // Spark.init();
    // Spark.awaitInitialization();
    // System.out.println("Server started at http://localhost:" + port);
    Datasource state = new CensusAPI();

    int port = 3232;
    Spark.port(port);

    // Initialize and start the server
    new Server(state);
    System.out.println("Server started at http://localhost:" + port);
  }
}
