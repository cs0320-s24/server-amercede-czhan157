package edu.brown.cs.student.main.server;

<<<<<<< Updated upstream
public class BroadbandHandler {
    
}

// idea: give it the name of a state/county, first time to access number value, 
// that's how you get query, with that query, you access the API a third time, 
// to get the broadband percentage


// Hi! Your broabandHandler should have query parameters 
// for the name of the target state and county. Exploring the ACS API 
// documentation will reveal that this isn't exactly what you need to
//  make a request to their API. As a result, you'll need to use the 
//  query parameters in order to obtain the information you DO need 
//  to make a request to the ACS API. Once you have this information 
//  and make the request, you then need to take it and send it back 
//  to your API. Please let me know if I need to clarify anything!

// caching - caching mimick from a package -> it'll be called by our main server class. 
// kinda calls the methods that we called. 
=======
//import edu.brown.cs.student.main.csv.broadband;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.datasource.*;
import spark.Request;
import spark.Response;
import spark.Route;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BroadbandHandler implements Route {

    private final Datasource state;

    public BroadbandHandler(Datasource state) {
        this.state = state;
    }


    @Override
    public Object handle(Request request, Response response) {
        Moshi moshi = new Moshi.Builder().build();

        //Maybe try to see if Object can be a bit more specific. Maybe another data type
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);

        //I need tp change what is below -> To weatherData equivalent
        JsonAdapter<BroadbandData> weatherDataAdapter = moshi.adapter(BroadbandData.class);


        //I'm thinking that this will not be a HashMap, but a different kind of map
        //So we don't run out of memory and can work with a caching system
        Map<String, Object> responseMap = new HashMap<>();

        String nameOfState = request.queryParams("Name of State"); //lat
        String nameOfCounty = request.queryParams("Name of County"); //lon


        if (nameOfState == null || nameOfCounty == null) {
            // Bad request! Send an error response.
            responseMap.put("query_nameOfState", nameOfState);
            responseMap.put("query_nameOfCounty", nameOfCounty);
            responseMap.put("type", "error");
            responseMap.put("error_type", "missing_parameter");
            responseMap.put("error_arg", nameOfState == null ? "nameOfState" : "nameOfCounty");
            return adapter.toJson(responseMap);
        }

        // Generate the reply
        try {
//            double lat_double = Double.parseDouble(lat);
//            double lon_double = Double.parseDouble(lon);
            TargetLocation location = new TargetLocation(nameOfState, nameOfCounty);
            // Low-level NWS API invocation isn't the job of this class!
            // Neither is caching! Just get the data from whatever the source is.
            BroadbandData data = state.getBroadbandPercentage(location);
            // Building responses *IS* the job of this class:
            responseMap.put("type", "success");

            responseMap.put("broadband", weatherDataAdapter.toJson(data));
            // Decision point; note the difference vs. this
            //responseMap.put("temperature", data);

            return adapter.toJson(responseMap);
        } catch (DatasourceException e) {
            // Issues getting the data. Return an error response.
            responseMap.put("query_nameOfCounty", nameOfCounty);
            responseMap.put("query_nameOfState", nameOfState);
            responseMap.put("type", "error");
            responseMap.put("error_type", "datasource");
            responseMap.put("details", e.getMessage());
            return adapter.toJson(responseMap);
        } catch (IllegalArgumentException e) {
            // Invalid geolocation, probably. Return an error response.
            responseMap.put("query_nameOfCounty", nameOfCounty);
            responseMap.put("query_nameOfState", nameOfState);
            responseMap.put("type", "error");
            responseMap.put("error_type", "bad_parameter");
            responseMap.put("details", e.getMessage());
            return adapter.toJson(responseMap);
        }

    }
}

//Map and Hashmap are different one does not look at duplicates.

// Hi! Your broabandHandler should have query parameters
// for the name of the target state and county. Exploring the ACS API
// documentation will reveal that this isn't exactly what you need to
//  make a request to their API. As a result, you'll need to use the
//  query parameters in order to obtain the information you DO need
//  to make a request to the ACS API. Once you have this information
//  and make the request, you then need to take it and send it back
//  to your API. Please let me know if I need to clarify anything!

// caching - caching mimick from a package -> it'll be called by our main server class.
// kinda calls the methods that we called.
>>>>>>> Stashed changes
