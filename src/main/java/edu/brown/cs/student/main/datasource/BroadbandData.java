package edu.brown.cs.student.main.datasource;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.csv.Broadband;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public record BroadbandData() {


    /**
     * This makes our client and request and
     * This will return a string that represents a response to our query
     * @return
     */
    /**
     * Deserializes JSON from the BoredAPI into an Activity object.
     *
     * @param jsonActivity
     * @return
     */
    public static Broadband deserializeActivity(String jsonActivity) {
        try {
            // Initializes Moshi
            Moshi moshi = new Moshi.Builder().build();

            // Initializes an adapter to an Activity class then uses it to parse the JSON.
            JsonAdapter<Broadband> adapter = moshi.adapter(Broadband.class);

            Broadband broadband = adapter.fromJson(jsonActivity);

            return broadband;
        }
        // Returns an empty activity... Probably not the best handling of this error case...
        // Notice an alternative error throwing case to the one done in OrderHandler. This catches
        // the error instead of pushing it up.
        catch (IOException e) {
            e.printStackTrace();
            return new Broadband();
        }
    }
    public static List<Broadband> deserializeBroadbandJson(String jsonList) throws IOException {
        List<Broadband> menu = new ArrayList<>();
        try {
            Moshi moshi = new Moshi.Builder().build();
            // notice the type and JSONAdapter parameterized type match the return type of the method
            // Since List is generic, we shouldn't just pass List.class to the adapter factory.
            // Instead, let's be more precise. Java has built-in classes for talking about generic types
            // programmatically.
            // Building libraries that use them is outside the scope of this class, but we'll follow the
            // Moshi docs'
            // template by creating a Type object corresponding to List<Ingredient>:
            Type listType = Types.newParameterizedType(List.class, Broadband.class);
            JsonAdapter<List<Broadband>> adapter = moshi.adapter(listType);

            List<Broadband> deserializedBroadbandList = adapter.fromJson(jsonList);

            return deserializedBroadbandList;
        }
        // From the Moshi Docs (https://github.com/square/moshi):
        //   "Moshi always throws a standard java.io.IOException if there is an error reading the JSON
        // document, or if it is malformed. It throws a JsonDataException if the JSON document is
        // well-formed, but doesn't match the expected format."
        catch (IOException e) {
            // In a real system, we wouldn't println like this, but it's useful for demonstration:
            System.err.println("BroadbandHandler: string wasn't valid JSON.");
            throw e;
        } catch (JsonDataException e) {
            // In a real system, we wouldn't println like this, but it's useful for demonstration:
            System.err.println("BroadbandHandler: JSON wasn't in the right format.");
            throw e;
        }
    }

    /**
     * Serializes a menu of Soups into a string. Also not used in the gearups. Generally,
     * serialization in Sprint 2 involves creating a useful response type for your API. You do not
     * necessarily have to serialize your types back into JSON, but we include this example to show
     * you how.
     *
     * @param menu
     * @return
     */
    public static String serializeBroadband(List<Broadband> broadbandList) {
        // Initialize Moshi
        Moshi moshi = new Moshi.Builder().build();
        // Create type for adapter
        Type listOfBroadbandType = Types.newParameterizedType(List.class, Broadband.class);
        // use adapter to parse a string.
        JsonAdapter<List<Broadband>> adapter = moshi.adapter(listOfBroadbandType);
        return adapter.toJson(broadbandList);
    }
    /**
     * Mixes up a fresh bowl of soup, based on a JSON object that says what ingredients are in it.
     * Yum!
     *
     * <p>This is not used in the Gearup, but this might be how one could deserialize a single Soup
     * from JSON into a Soup object.
     *
     * @param jsonList the ingredients as a JSON list
     * @throws JsonDataException if the given JSON doesn't conform to the soup specification (we're
     *     re-using Moshi's JsonDataException here; this may or may not be a good choice in reality)
     * @throws IOException if the given string isn't valid JSON
     * @return the Soup object
     */
    public static Broadband deserializeBroadband(String jsonList) throws JsonDataException, IOException {

        // Create an adapter to read the json string (hopefully) into a Soup object.
        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<Broadband> broadbandJsonAdapter = moshi.adapter(Broadband.class);
        // Finally read the json string:
        try {
            Broadband broadbandJava = broadbandJsonAdapter.fromJson(jsonList);
            // In the beginning, the soup is empty. There's nothing in the pot.

            return broadbandJava;
        } catch (IOException e) {
            // In a real system, we wouldn't println like this, but it's useful for demonstration:
            System.err.println("BroadbandHandler: string wasn't valid JSON.");
            throw e;
        } catch (JsonDataException e) {
            // In a real system, we wouldn't println like this, but it's useful for demonstration:
            System.err.println("BroadbandHandler: JSON didn't have the right fields.");
            throw e;
        }
    }

    /**
     * Serializes the ingredients in a bowl soup into a Json object for sending across the 'net. The
     * result does not include any soup properties---we're just sending the ingredients.
     *
     * <p>Also not used in the gearup! Just for illustrative purposes
     *
     * @param soup The soup to serialize into a String
     * @return the serialized Json list representing the ingredients
     */
    public static String serializeBroadband(Broadband broadband) {
        // as in fromJson, we need to work with ingredients.
        // The polymorphic factory will automatically _insert_ the "type" field
        Moshi moshi = new Moshi.Builder().build();

        // Uses a similar pattern but turns it toJson and returns it as a string
        JsonAdapter<Broadband> adapter = moshi.adapter(Broadband.class);
        return adapter.toJson(broadband);
    }

    /**
     * You will likely not need to do this in Sprint 2 as the JSON / data will come from your HTTP
     * Request... Think about how you can combine these patterns to deserialize the data you will need
     * for Sprint 2.
     *
     * @param filepath
     * @return
     * @throws IOException
     */
    public static String readInJson(String filepath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
            return "Error in reading JSON";
        }
    }
}
