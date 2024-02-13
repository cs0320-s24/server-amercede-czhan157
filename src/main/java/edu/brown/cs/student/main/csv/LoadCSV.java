package edu.brown.cs.student.main.csv;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoadCSV {
    private static final String dirPath = "./data/";
    // have user potentially input the allowed directory path
    //
    private final Set<CSVParser> parserSet;

    public LoadCSV(Set<CSVParser> parserSet) {
        this.parserSet = parserSet;
    }

    public Object handle(Request request, Response response) {
        response.header("Label", "Label");
        Map<String, String[]> params = request.queryMap().toMap();
        //return success or failure response
        //response should be as specific as possible
    }

    // server isn't main function, but if it were: server is the manager, controls all
    // endpoints it has - endpoints are classes servers will connect to
    // you need to have it implement route -- route transforms normal class into an endpoin
    // route will make you create a handle method




}