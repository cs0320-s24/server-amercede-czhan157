package edu.brown.cs.student.main.datasource;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class BroadbandPercent {

    private List<List<String>> theListOfBroadbandPercent;
    public BroadbandPercent(String stateNum, String countyNum) throws IOException, URISyntaxException, InterruptedException {
        String stateCode =  this.sendRequestForTheBroadbandPercent(stateNum, countyNum);
        theListOfBroadbandPercent = this.deserializeBroadbandPercent(stateCode);
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

        return sentBroadbandApiResponse.body();
        //This would then be a json thing that needs to be serialized, so that we can search for what we need
    }

    private List<List<String>> deserializeBroadbandPercent(String jsonList) throws JsonDataException, IOException {

        // Create an adapter to read the json string (hopefully) into a Soup object.
        Moshi moshi = new Moshi.Builder().build();
        Type listWeNeed = Types.newParameterizedType(List.class, List.class);
        JsonAdapter<List<List<String>>> listOfJsonBroadbandPercent = moshi.adapter(listWeNeed);
        // Finally read the json string:
        try {
            List<List<String>> broadbandPercentListJava = listOfJsonBroadbandPercent.fromJson(jsonList);
            // In the beginning, the soup is empty. There's nothing in the pot.

            return broadbandPercentListJava;
        } catch (IOException e) {
            // In a real system, we wouldn't println like this, but it's useful for demonstration:
            System.err.println("BroadbandPercent: string wasn't valid JSON.");
            throw e;
        } catch (JsonDataException e) {
            // In a real system, we wouldn't println like this, but it's useful for demonstration:
            System.err.println("BroadbandPercent: JSON didn't have the right fields.");
            throw e;
        }

    }

    public List<List<String>> getBroadbandPercent(){
        return theListOfBroadbandPercent;
    }
}
