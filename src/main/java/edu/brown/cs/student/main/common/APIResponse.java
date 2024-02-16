package edu.brown.cs.student.main.common;

import com.squareup.moshi.Moshi;

public interface APIResponse {
  default String serialize() {
    Moshi moshi = new Moshi.Builder().build();

    return moshi.adapter(this.getClass()).toJson(null);

    // return moshi.adapter(APIResponse.class).toJson(this);
  }
}
