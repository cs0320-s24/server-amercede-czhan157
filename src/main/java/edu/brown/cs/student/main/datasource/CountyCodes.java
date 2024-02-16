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

public class CountyCodes {

  private List<List<String>> theListOfCountyCodes;

  public CountyCodes(String stateNum) throws URISyntaxException, IOException, InterruptedException {
    String jsonCountyCode = this.sendRequestForTheCounties(stateNum);
    theListOfCountyCodes = this.deserializeCountyCodes(jsonCountyCode);
  }

  public String sendRequestForTheCounties(String stateNum)
      throws URISyntaxException, IOException, InterruptedException {
    // This is actually building the API request
    HttpRequest buildBroadbandApiRequest =
        HttpRequest.newBuilder()
            .uri(
                new URI(
                    "https://api.census.gov/data/2010/dec/sf1?get=NAME&for=county:*&in=state:"
                        + stateNum))
            .GET()
            .build();

    // This builds the client so it can receive and send a response
    HttpResponse<String> sentBroadbandApiResponse =
        HttpClient.newBuilder()
            .build()
            .send(buildBroadbandApiRequest, HttpResponse.BodyHandlers.ofString());

    return sentBroadbandApiResponse.body();
    // This would then be a json thing that needs to be serialized, so that we can search for what
    // we need
  }

  private List<List<String>> deserializeCountyCodes(String jsonList)
      throws JsonDataException, IOException {

    // Create an adapter to read the json string (hopefully) into a Soup object.
    Moshi moshi = new Moshi.Builder().build();
    Type listWeNeed = Types.newParameterizedType(List.class, List.class);
    JsonAdapter<List<List<String>>> listOfJsonCountyCodes = moshi.adapter(listWeNeed);
    // Finally read the json string:
    try {
      List<List<String>> countyCodeListJava = listOfJsonCountyCodes.fromJson(jsonList);
      // In the beginning, the soup is empty. There's nothing in the pot.

      return countyCodeListJava;
    } catch (IOException e) {
      // In a real system, we wouldn't println like this, but it's useful for demonstration:
      System.err.println("CountyCodes: string wasn't valid JSON.");
      throw e;
    } catch (JsonDataException e) {
      // In a real system, we wouldn't println like this, but it's useful for demonstration:
      System.err.println("CountyCodes: JSON didn't have the right fields.");
      throw e;
    }
  }

  public List<List<String>> getCountyCodes() {
    return theListOfCountyCodes;
  }
}
