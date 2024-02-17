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

/**
 * Broadband handler handles getting the query from the web browser and returning a response.
 * It uses the classes StateCodes, CountyCodes and BroadbandPercent to handle most of the functionality of
 * getting the actual broadband percent of the state and county the user queries
 */
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
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);

    Map<String, Object> responseMap = new HashMap<>();

    String nameOfState = request.queryParams("statename");
    String nameOfCounty = request.queryParams("countyname");

    if (nameOfState == null || nameOfCounty == null) {
      // Bad request! Send an error response.
      responseMap.put("query_nameOfState", nameOfState);
      responseMap.put("query_nameOfCounty", nameOfCounty);
      responseMap.put("type", "error");
      responseMap.put("error_type", "missing_parameter");
      responseMap.put("error_arg", nameOfState == null ? "nameOfState" : "nameOfCounty");
      return adapter.toJson(responseMap);
    }

    // This for loop to find the state code  might need to happen again and again
    String stateQuery = nameOfState;
    String currentStateNum = "";
    for (List<String> i : theListOfStateCodes) {
      if (i.contains(stateQuery)) {
        currentStateNum = i.get(1);
      }
    }

    // This is how we get the county codes
    if (currentStateNum.equals("")) {
      System.err.println("There is something wrong with getting the state num");
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

    // Generate the reply
    try {
      responseMap.put("type", "success");
      responseMap.put(
          "broadband", theListWeNeedOfBroadbandPercent);

      return adapter.toJson(responseMap);
    } catch (IllegalArgumentException e) {

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
