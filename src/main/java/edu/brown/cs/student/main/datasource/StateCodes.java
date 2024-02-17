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
 * StateCodes handles getting the list of State Codes for the BroadbandHandler. It requests it from the
 * census API and deserializes it, so that the getStateCodes function can return a List of list of Strings
 * that contains state codes and that Java can actually use.
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
    // This would then be a json thing that needs to be serialized, so that we can search for what
    // we need
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
      // In the beginning, the soup is empty. There's nothing in the pot.

      return stateCodeListJava;
    } catch (IOException e) {
      // In a real system, we wouldn't println like this, but it's useful for demonstration:
      System.err.println("StateCodes: string wasn't valid JSON.");
      throw e;
    } catch (JsonDataException e) {
      // In a real system, we wouldn't println like this, but it's useful for demonstration:
      System.err.println("StateCodes: JSON didn't have the right fields.");
      throw e;
    }
  }

  public List<List<String>> getStateCodes() {
    return theListOfStateCodes;
  }
}
