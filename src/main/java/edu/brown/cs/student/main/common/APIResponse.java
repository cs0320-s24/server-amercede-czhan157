package edu.brown.cs.student.main.common;

public interface APIResponse {
  default String serialize() {
    //   Moshi moshi = new Moshi.Builder().build();
    //       String json = moshi.adapter(this.getClass()).toJSON((APIResponse)this);

    //       JsonAdapter<List<List<String>>> listOfJsonBroadbandPercent =
    // moshi.adapter(this.getClass());
    //   // Finally read the json string:
    //   try {
    //     List<List<String>> broadbandPercentListJava =
    // listOfJsonBroadbandPercent.fromJson(jsonList);
    //       System.out.println(json);
    //       return json;
    // }
    return "FAILURe";
  }
}
