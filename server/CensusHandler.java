package server;
import spark.Request; 
import spark.Response;
import spark.Route; 
public class CensusHandler implements Route {
    // the handler is in charge retrieving the parameters that the user passes in and 
    // returning it back to the user
    // datasource grabs parameters extracted by handler calls API
    // handler calls datasource, datasource returns to handler, then handler serializes 
    // class for serialization, serialization is moshi 
}