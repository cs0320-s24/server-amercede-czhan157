package edu.brown.cs.student.main.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.datasource.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class BroadbandHandler implements Route {

  private final Datasource state;

  public BroadbandHandler(Datasource state) {
    this.state = state;
  }

  @Override
  public Object handle(Request request, Response response)
      throws IOException, URISyntaxException, InterruptedException {
    // This is everything for the state codes
    // We only need to do this first part once

    StateCodes theOfficialStateCodes = new StateCodes();
    List<List<String>> theListOfStateCodes = theOfficialStateCodes.getStateCodes();

    Moshi moshi = new Moshi.Builder().build();
    //
    //        //Maybe try to see if Object can be a bit more specific. Maybe another data type
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
    //
    //        //I need tp change what is below -> To weatherData equivalent

    Type listWeNeedForBroadbandPercent = Types.newParameterizedType(List.class, List.class);
    JsonAdapter<List<String>> jsonBroadbandPercentAdapter =
        moshi.adapter(listWeNeedForBroadbandPercent);

    // I'm thinking that this will not be a HashMap, but a different kind of map
    // So we don't run out of memory and can work with a caching system
    Map<String, Object> responseMap = new HashMap<>();

    String nameOfState = request.queryParams("statename"); // lat
    String nameOfCounty = request.queryParams("countyname"); // lon

    if (nameOfState == null || nameOfCounty == null) {
      // Bad request! Send an error response.
      responseMap.put("query_nameOfState", nameOfState);
      responseMap.put("query_nameOfCounty", nameOfCounty);
      responseMap.put("type", "error");
      responseMap.put("error_type", "missing_parameter");
      responseMap.put("error_arg", nameOfState == null ? "nameOfState" : "nameOfCounty");
      return adapter.toJson(responseMap);
    }

    // This might need to happen again and again
    String stateQuery = nameOfState;
    String currentStateNum = "";
    for (List<String> i : theListOfStateCodes) {
      if (i.contains(stateQuery)) {
        currentStateNum = i.get(1);
      }
    }

    // This is how we get the county codes
    if (currentStateNum.equals("")) { // We might not need this.
      System.out.println("There is something wrong with getting the state num");
    }
    CountyCodes theOfficialCountyCodes = new CountyCodes(currentStateNum);
    List<List<String>> theListOfCountyCodes = theOfficialCountyCodes.getCountyCodes();
    String countyQuery = nameOfCounty + ", " + stateQuery;
    String currentCountyNum = "";
    for (List<String> i : theListOfCountyCodes) {
      if (i.contains(countyQuery)) {
        currentCountyNum = i.get(2);
      }
    }

    // Now we are getting the broadband percentage
    BroadbandPercent theOfficialBroadbandPercents =
        new BroadbandPercent(currentStateNum, currentCountyNum);
    List<List<String>> theListOfBroadbandPercents =
        theOfficialBroadbandPercents.getBroadbandPercent();
    List<String> theListWeNeedOfBroadbandPercent = theListOfBroadbandPercents.get(1);

    System.out.println("Pre-try");
    // Generate the reply
    try {

      System.out.println("Try 1");
      //            double lat_double = Double.parseDouble(lat);
      //            double lon_double = Double.parseDouble(lon);
      TargetLocation location = new TargetLocation(nameOfState, nameOfCounty);

      System.out.println("Try 2");
      // Low-level NWS API invocation isn't the job of this class!
      // Neither is caching! Just get the data from whatever the source is.
      BroadbandData data = state.getBroadbandPercentage(location);


      System.out.println("Try 3");
      // Building responses *IS* the job of this class:
      responseMap.put("type", "success");


      System.out.println("Try 4");
      responseMap.put(
          "broadband", theListWeNeedOfBroadbandPercent);


      System.out.println("Try 5");
      // Decision point; note the difference vs. this
      // responseMap.put("temperature", data);
      return adapter.toJson(responseMap);
    } catch (DatasourceException e) {

      System.out.println("Catch 1");
      // Issues getting the data. Return an error response.
      responseMap.put("query_nameOfCounty", nameOfCounty);
      responseMap.put("query_nameOfState", nameOfState);
      responseMap.put("type", "error");
      responseMap.put("error_type", "datasource");
      responseMap.put("details", e.getMessage());
      return adapter.toJson(responseMap);
    } catch (IllegalArgumentException e) {

      System.out.println("Catch 2");
      // Invalid nameOfCounty or nameOFState, probably. Return an error response.
      responseMap.put("query_nameOfCounty", nameOfCounty);
      responseMap.put("query_nameOfState", nameOfState);
      responseMap.put("type", "error");
      responseMap.put("error_type", "bad_parameter");
      responseMap.put("details", e.getMessage());
      return adapter.toJson(responseMap);
    }
    catch(Exception e){
      return e.getMessage();
    }
  }
}
