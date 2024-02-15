package edu.brown.cs.student.main.server;

//import edu.brown.cs.student.main.csv.broadband;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.datasource.*;
import spark.Request;
import spark.Response;
import spark.Route;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    private String sendRequestForTheState() throws URISyntaxException, IOException, InterruptedException{
        //This is actually building the API request
        HttpRequest buildBroadbandApiRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=state:*"))
                .GET()
                .build();

        //This builds the client so it can receive and send a response
        HttpResponse<String> sentBroadbandApiResponse =
                HttpClient.newBuilder()
                        .build()
                        .send(buildBroadbandApiRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(sentBroadbandApiResponse);
        System.out.println(sentBroadbandApiResponse.body());
        return sentBroadbandApiResponse.body();
        //This would then be a json thing that needs to be serialized, so that we can search for what we need
    }

    public String sendRequestForTheCounties(String stateNum) throws URISyntaxException, IOException, InterruptedException{
        //This is actually building the API request
        HttpRequest buildBroadbandApiRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=county:*&in=state:" + stateNum))
                .GET()
                .build();

        //This builds the client so it can receive and send a response
        HttpResponse<String> sentBroadbandApiResponse =
                HttpClient.newBuilder()
                        .build()
                        .send(buildBroadbandApiRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(sentBroadbandApiResponse);
        System.out.println(sentBroadbandApiResponse.body());
        return sentBroadbandApiResponse.body();
        //This would then be a json thing that needs to be serialized, so that we can search for what we need
    }

    public String sendRequestForTheBroadbandPercent(String stateNum, String countyNum) throws URISyntaxException, IOException, InterruptedException {
        //This is actually building the API request
        HttpRequest buildBroadbandApiRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.census.gov/data/2021/acs/acs1/subject/variables?get=NAME,S2802_C03_022E" +
                        "&for=county:" + countyNum + "&in=state:" + stateNum))
                .GET()
                .build();

        //This builds the client so it can receive and send a response
        HttpResponse<String> sentBroadbandApiResponse =
                HttpClient.newBuilder()
                        .build()
                        .send(buildBroadbandApiRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(sentBroadbandApiResponse);
        System.out.println(sentBroadbandApiResponse.body());
        return sentBroadbandApiResponse.body();
        //This would then be a json thing that needs to be serialized, so that we can search for what we need
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
