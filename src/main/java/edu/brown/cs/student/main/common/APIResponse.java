package edu.brown.cs.student.main.common;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

public interface APIResponse {
  default String serialize() {
       Moshi moshi = new Moshi.Builder().build();
           String json = moshi.adapter(this.getClass()).toJson(this.clone());

           JsonAdapter<List<List<String>>> listOfJsonBroadbandPercent =
     moshi.adapter(this.getClass());
       // Finally read the json string:
       try {
         List<List<String>> broadbandPercentListJava =
     listOfJsonBroadbandPercent.fromJson(jsonList);
           System.out.println(json);
           return json;
     }
    return "FAILURe";
  }
}
