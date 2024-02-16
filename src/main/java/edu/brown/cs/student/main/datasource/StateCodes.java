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

/**
 * State Codes is a class that handles getting the state codes from the CensusAPI.
 * It also turns json objects into java from what it gets from the API in deserializeStateCodes
 * It returns a list of list of strings in getStateCodes
 */
public class StateCodes {

  private List<List<String>> theListOfStateCodes;

  public StateCodes() throws URISyntaxException, IOException, InterruptedException {
    String stateCode = this.sendRequestForTheState();
    theListOfStateCodes = this.deserializeStateCodes(stateCode);
  }

  private String sendRequestForTheState()
      throws URISyntaxException, IOException, InterruptedException {
    // This is actually building the API request
    HttpRequest buildBroadbandApiRequest =
        HttpRequest.newBuilder()
            .uri(new URI("https://api.census.gov/data/2010/dec/sf1?get=NAME&for=state:*"))
            .GET()
            .build();

    // This builds the client so it can receive and send a response
    HttpResponse<String> sentBroadbandApiResponse =
        HttpClient.newBuilder()
            .build()
            .send(buildBroadbandApiRequest, HttpResponse.BodyHandlers.ofString());

    return sentBroadbandApiResponse.body();
  }

  private List<List<String>> deserializeStateCodes(String jsonList)
      throws JsonDataException, IOException {

    // Create an adapter to read the json string (hopefully) into a Soup object.
    Moshi moshi = new Moshi.Builder().build();
    Type listWeNeed = Types.newParameterizedType(List.class, List.class);
    JsonAdapter<List<List<String>>> listOfJsonStateCodes = moshi.adapter(listWeNeed);
    // Finally read the json string:
    try {

      List<List<String>> stateCodeListJava = listOfJsonStateCodes.fromJson(jsonList);
      return stateCodeListJava;
    } catch (IOException e) {
      System.err.println("StateCodes: string wasn't valid JSON.");
      throw e;
    } catch (JsonDataException e) {
      System.err.println("StateCodes: JSON didn't have the right fields.");
      throw e;
    }
  }

  public List<List<String>> getStateCodes() {
    return theListOfStateCodes;
  }
}
