package edu.brown.cs.student.main.server;

import static spark.Spark.after;
import spark.Spark;
import java.util.HashSet;
import java.util.Set;

import edu.brown.cs.student.main.csv;
public class Server {
    public static void main(String[] args) {
        Spark.port(3232);
        after(
                (request, response) -> {
                    response.header("Access-Control-Allow-Origin", "*");
                    response.header("Access-Control-Allow-Origin", "loadcsv");
                    response.header("Access-Control-Allow-Origin", "viewcsv");
                    response.header("Access-Control-Allow-Origin", "searchcsv");
                };)

        String csvUtility = new String();
        Spark.get("loadcsv", new LoadCSV(csvParser(csvUtility)));
        Spark.get("viewCSV", new ViewCSV(CSVParser(csvUtility)));
        Spark.get("loadcsv", new SearchCSV(csvParser(csvUtility)));
        Spark.get("census", new broadband());

        Spark.get(
                "*",
                (request, response) ->
                {
                    response.header("Content-Type", "application/json");
                    response.body(
                            new ServerException("sdf")
                );
                    return null;
                });
        Spark.init();
        Spark.awaitInitialization();
        System.out.println("Server started.");

    }
}

